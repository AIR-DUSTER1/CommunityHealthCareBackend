package org.dromara.services.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 服务订单对象 ch_services_order
 *
 * @author AIR-DUSTER1
 * @date 2024-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ch_services_order")
public class ChServicesOrder extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 服务订单ID
     */
    @TableId(value = "services_order_id")
    private Long servicesOrderId;

    /**
     * 服务ID
     */
    private Long servicesId;

    /**
     * 服务名称
     */
    private String servicesName;

    /**
     * 下单用户ID
     */
    private Long userId;

    /**
     * 下单用户昵称
     */
    private String userName;

    /**
     * 订单金额
     */
    private Long price;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 支付方式
     */
    private String paymentType;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 订单状态
     */
    private String status;


}
