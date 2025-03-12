package org.dromara.services.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.pay.config.AliPayConfig;
import org.dromara.common.redis.utils.QueueUtils;
import org.dromara.common.web.core.BaseController;
//import org.dromara.services.config.AliPayConfig;
import org.dromara.services.domain.ChServices;
import org.dromara.services.domain.ChServicesOrder;
import org.dromara.services.domain.bo.ChServicesOrderBo;
import org.dromara.services.domain.dto.AliPayDTO;
import org.dromara.services.domain.dto.BuyDTO;
import org.dromara.services.domain.dto.PayDTO;
import org.dromara.services.domain.vo.ChServicesAccountVo;
import org.dromara.services.domain.vo.ChServicesOrderVo;
import org.dromara.services.domain.vo.ChServicesVo;
import org.dromara.services.service.impl.AliPayServiceImpl;
import org.dromara.services.service.impl.ChServicesAccountServiceImpl;
import org.dromara.services.service.impl.ChServicesOrderServiceImpl;
import org.dromara.services.service.impl.ChServicesServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
/**
 * @author AIR-DUSTER1
 * @date 2024/12/31
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/alipay")
public class AliPayController extends BaseController {

	private final AliPayConfig aliPayConfig;
	private final AlipayClient alipayClient;
	private final ChServicesOrderServiceImpl orderService;
	private final ChServicesAccountServiceImpl accountService;
    private final AliPayServiceImpl payService;
    private final ChServicesServiceImpl service;
//	private final WebSocketServer webSocketServer;

	@GetMapping("/topay")
    @Transactional
	public String pay(PayDTO dto, Model model) {
        ChServices service = payService.queryService(dto.getServiceId());
        ChServicesOrderBo order = payService.createOrderBo(dto,service);
        ChServicesAccountVo account = accountService.queryById(dto.getAccountId());
		if (account == null) {
			throw new RuntimeException("账户不存在");
		}

        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
		AlipayTradeWapPayModel bizModel = new AlipayTradeWapPayModel();
		bizModel.setOutTradeNo(String.valueOf(order.getServicesOrderId()));
		bizModel.setTotalAmount(String.valueOf(dto.getPrice()));
		bizModel.setSubject("社区充值");
		bizModel.setProductCode("FAST_INSTANT_TRADE_PAY");
		request.setBizModel(bizModel);
		request.setNotifyUrl(aliPayConfig.getNotifyUrl());
		AlipayTradeWapPayResponse response = null;
        order.setStatus("1");// 支付中
        payService.PublishDelayedOrder(order);
		try {
			// 完成签名并执行请求
			response = alipayClient.pageExecute(request);
			if (response.isSuccess()) {
				log.debug("调用成功");
				model.addAttribute("body", response.getBody());
				return "alipay";
			} else {
				log.error("调用失败");
				log.error(response.getMsg());
                order.setPayStatus("3");
				orderService.changeOrderStatus(order);
				return null;
			}
		} catch (AlipayApiException e) {
			log.error("调用异常");
            order.setPayStatus("3");
            orderService.changeOrderStatus(order);
			return null;
		}
	}

	@ResponseBody
	@GetMapping("/apppay")
    @RepeatSubmit()
    @Transactional
        public AlipayTradeAppPayResponse alipay(PayDTO dto) {
        ChServices service = payService.queryService(dto.getServiceId());
        ChServicesOrderBo order = payService.createOrderBo(dto,service);
        Boolean b = orderService.insertByBo(order);
        if (!b){
            return null;
        }
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel bizModel = new AlipayTradeAppPayModel();
		bizModel.setOutTradeNo(String.valueOf(order.getServicesOrderId()));
		bizModel.setTotalAmount(String.valueOf(dto.getPrice()));
		bizModel.setSubject("社区充值");
		bizModel.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(bizModel);
		request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        order.setStatus("1");// 支付中
        payService.PublishDelayedOrder(order);
		try {
			return alipayClient.sdkExecute(request);
		} catch (AlipayApiException e) {
		    order.setPayStatus("3");
            orderService.changeOrderStatus(order);
			throw new RuntimeException(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping("/notify")
    @SaIgnore
    @Transactional
	public String notify(HttpServletRequest request) {
		try {
			Map<String, String> params = new HashMap<>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (String name : requestParams.keySet()) {
				params.put(name, request.getParameter(name));
			}

			boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayConfig.getAlipayPublicKey(),
					AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
			if (signVerified) {
				log.info("签名验证成功");
				// TODO 修改业务
                ChServicesOrderBo order = new ChServicesOrderBo();
                order.setTraceNo(params.get("trade_no"));
                order.setUpdateTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
                order.setServicesOrderId(params.get("out_trade_no"));
                order.setPayStatus("2");//支付成功
                order.setStatus("3");//已付款
				orderService.changeOrderStatus(order);
                QueueUtils.removeDelayedQueueObject("payDelay", order.getServicesOrderId());
                QueueUtils.destroyDelayedQueue("payDelay");
                ChServicesOrderVo chServicesOrderVo = orderService.queryById(order.getServicesOrderId());
                ChServicesVo chServicesVo = service.queryById(order.getServiceId());
                String classification = chServicesVo.getClassification();
                if (classification.equals("充值服务")){
                    accountService.updatePrice(chServicesOrderVo.getUserId(), chServicesOrderVo.getPrice(),true);
                }else {
                    accountService.updatePrice(chServicesOrderVo.getUserId(), chServicesOrderVo.getPrice(),false);
                }
			} else {
				log.error("签名验证失败");
			}
		} catch (Exception e) {
			log.error("签名验证异常", e);
			throw new RuntimeException(e.getMessage());
		}
//		webSocketServer.sendToAllClient("success");
		return "success";
	}
    @PostMapping("/buy")
    @RepeatSubmit()
    @Log(title = "支付", businessType = BusinessType.INSERT)
    @Transactional
    public R<Void> buy(@RequestBody BuyDTO dto) {
        ChServices service = payService.queryService(dto.getServiceId());
        ChServicesOrderBo order = payService.createOrderBo(dto, service);
        Boolean b = orderService.insertByBo(order);
        if (b){
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            AlipayTradeAppPayModel bizModel = new AlipayTradeAppPayModel();
		    bizModel.setOutTradeNo(String.valueOf(order.getServicesOrderId()));
            bizModel.setTotalAmount(String.valueOf(order.getPrice()));
            bizModel.setSubject(order.getServicesName());
            bizModel.setProductCode("QUICK_MSECURITY_PAY");
            request.setBizModel(bizModel);
            request.setNotifyUrl(aliPayConfig.getNotifyUrl());
            try {
                //这里和普通的接口调用不同，使用的是sdkExecute
                AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
                log.info("支付宝响应: {}", response.getBody());
            } catch (AlipayApiException e) {
                log.error("支付宝支付失败", e);
                throw new RuntimeException(e.getMessage());
            }
            order.setPayStatus("1");// 支付中
            payService.PublishDelayedOrder(order);
        }
        return toAjax(orderService.changeOrderStatus(order));
    }
    @PostMapping("/refund")
    @RepeatSubmit()
    @Log(title = "支付", businessType = BusinessType.INSERT)
    public R<Void> Refund(){
        ChServicesOrderBo order = new ChServicesOrderBo();
        return toAjax(orderService.changeOrderStatus(order));
    }
}
