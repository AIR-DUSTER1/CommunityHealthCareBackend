package org.dromara.services.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.services.config.AliPayConfig;
import org.dromara.services.domain.dto.AliPayDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaokai54
 * @date 2024/9/3
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/alipay")
public class AliPayController {

	private final AliPayConfig aliPayConfig;
	private final AlipayClient alipayClient;
//	private final SysOrderService orderService;
//	private final SysCardService cardService;
//	private final WebSocketServer webSocketServer;

	@GetMapping("/topay")
	public String pay(AliPayDTO dto, Model model) {
//		OrderDTO orderDTO = new OrderDTO();
//		orderDTO.setTotalAmount(new BigDecimal(dto.getTotalAmount()));
//		SysCard card = cardService.selectByCardNo(dto.getCardNo());
//		if (card == null) {
//			throw new FnException("卡号不存在");
//		}
//		orderDTO.setCardNo(dto.getCardNo());
//		OrderSubmitVO order = orderService.submitOrder(orderDTO);

		AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
		AlipayTradeWapPayModel bizModel = new AlipayTradeWapPayModel();
//		bizModel.setOutTradeNo(order.getOrderNo());
		bizModel.setTotalAmount(dto.getTotalAmount());
		bizModel.setSubject("飞鸟充值");
		bizModel.setProductCode("FAST_INSTANT_TRADE_PAY");
		request.setBizModel(bizModel);
		request.setNotifyUrl(aliPayConfig.getNotifyUrl());
		AlipayTradeWapPayResponse response = null;
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
//				orderService.cancelOrder(order.getOrderNo(), "支付失败");
				return null;
			}
		} catch (AlipayApiException e) {
			log.error("调用异常");
//			orderService.cancelOrder(order.getOrderNo(), "支付失败");
			return null;
		}
	}

	@ResponseBody
	@GetMapping("/apppay")
	public AlipayTradeAppPayResponse alipay(AliPayDTO dto) {
//		OrderDTO orderDTO = new OrderDTO();
//		orderDTO.setTotalAmount(new BigDecimal(dto.getTotalAmount()));
//		orderDTO.setCardNo(dto.getCardNo());
//		OrderSubmitVO order = orderService.submitOrder(orderDTO);
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel bizModel = new AlipayTradeAppPayModel();
//		bizModel.setOutTradeNo(order.getOrderNo());
		bizModel.setTotalAmount(dto.getTotalAmount());
		bizModel.setSubject("飞鸟充值");
		bizModel.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(bizModel);
		request.setNotifyUrl(aliPayConfig.getNotifyUrl());
		try {
			return alipayClient.sdkExecute(request);
		} catch (AlipayApiException e) {
//			orderService.cancelOrder(order.getOrderNo(), "支付失败");
//			throw new FnException(e.getMessage());
		}
        return null;
	}

	@ResponseBody
	@PostMapping("/notify")
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
				PayOrderDTO payOrderDTO = new PayOrderDTO();
				payOrderDTO.setPayType("支付宝充值");
//				payOrderDTO.setTraceNo(params.get("trade_no"));
//				payOrderDTO.setPayTime(LocalDateTime.now());
//				payOrderDTO.setOrderNo(params.get("out_trade_no"));
//				payOrderDTO.setPayStatus(2);
//				orderService.changeOrderStatus(payOrderDTO);
//				String cardNo = orderService.selectByOrderNo(params.get("out_trade_no")).getCardNo();
//				cardService.addMoney(cardNo, new BigDecimal(params.get("total_amount")));
			} else {
				log.error("签名验证失败");
			}
		} catch (Exception e) {
			log.error("签名验证异常", e);
//			throw new FnException(e.getMessage());
		}
//		webSocketServer.sendToAllClient("success");
		return "success";
	}
}
