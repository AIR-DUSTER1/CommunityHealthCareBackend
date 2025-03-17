package org.dromara.services.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alipay.api.response.AlipayTradeQueryResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.redis.utils.QueueUtils;
import org.dromara.services.domain.ChServices;
import org.dromara.services.domain.ChServicesOrder;
import org.dromara.services.domain.bo.ChServicesOrderBo;
import org.dromara.services.domain.dto.BuyDTO;
import org.dromara.services.domain.dto.PayDTO;
import org.dromara.services.domain.vo.ChServicesOrderVo;
import org.dromara.services.mapper.AliPayMapper;
import org.dromara.services.mapper.ChServicesAccountMapper;
import org.dromara.services.service.AliPayService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AliPayServiceImpl implements AliPayService {

    private final ChServicesAccountMapper chServicesAccountMapper;
    private final AliPayMapper aliPayMapper;
    private final ChServicesOrderServiceImpl orderService;

    @Override
    public String userNameByUserId(Long userId) {
        return chServicesAccountMapper.selectUserByUserId(userId);
    }
    public ChServices queryService(Long serviceId) {
        return aliPayMapper.userNameByUserId(serviceId);
    }
    public ChServicesOrder createOrder(BuyDTO dto, ChServices service) {
        ChServicesOrder order = new ChServicesOrder();
        order.setServicesOrderId(UUID.randomUUID().toString().replace("-", "").substring(0, 32));
        order.setUserId(dto.getUserId());
        order.setUserName(userNameByUserId(dto.getUserId()));
        order.setServiceId(dto.getServiceId());
        order.setServicesName(service.getName());
        order.setPrice(service.getPrice());
        order.setPaymentType("0"); // 支付宝支付
        order.setPayStatus("0"); // 未支付
        order.setStatus("0"); // 待付款
        order.setRemark("购买" + service.getName() + "服务");
        return order;
    }
    public ChServicesOrder createOrder(PayDTO dto, ChServices service) {
        ChServicesOrder order = new ChServicesOrder();
        order.setServicesOrderId(UUID.randomUUID().toString().replace("-", "").substring(0, 32));
        order.setUserId(dto.getUserId());
        order.setUserName(userNameByUserId(dto.getUserId()));
        order.setServiceId(dto.getServiceId());
        order.setServicesName(service.getName());
        order.setPrice(service.getPrice());
        order.setPaymentType("0"); // 支付宝支付
        order.setPayStatus("0"); // 未支付
        order.setStatus("0"); // 待付款
        order.setRemark("购买" + service.getName() + "服务");
        return order;
    }
    @PostConstruct
    public void InitDelayedOrder() {
        QueueUtils.subscribeBlockingQueue("payDelay", (String orderNum) -> {
            // 观察接收时间
            log.info("通道: {}, 收到数据: {}", "payDelay", orderNum);
            return CompletableFuture.runAsync(() -> {
                // 异步处理数据逻辑 不要在上方处理业务逻辑
                log.info("数据处理: {}", orderNum);
                ChServicesOrder chServicesOrder = orderService.queryById(orderNum);
                chServicesOrder.setPayStatus("4");
                chServicesOrder.setStatus("4");
                chServicesOrder.setRemark("订单超时未支付");

//                ChServicesOrderBo orderBo = new ChServicesOrderBo();
//                BeanUtil.copyProperties(chServicesOrderVo, orderBo);
                if (chServicesOrder != null) {
                    orderService.changeOrderStatus(chServicesOrder);
                    QueueUtils.destroyDelayedQueue("payDelay");
                }
            });
        }, true);
    }
    public void PublishDelayedOrder(ChServicesOrder order) {
        QueueUtils.addDelayedQueueObject("payDelay", order.getServicesOrderId(), 600, TimeUnit.SECONDS);
    }
}
