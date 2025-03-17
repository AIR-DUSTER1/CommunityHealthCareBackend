package org.dromara.services.domain.bo;

import org.dromara.services.domain.ChServicesOrder;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * 服务订单业务对象 ch_services_order
 *
 * @author AIR-DUSTER1
 * @date 2024-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ChServicesOrder.class, reverseConvertGenerate = false)
public class ChServicesOrderBo extends BaseEntity {

    /**
     * 服务订单ID
     */
    @NotNull(message = "服务订单ID不能为空", groups = { EditGroup.class })
    private String servicesOrderId;
    /**
     * 服务ID
     */
    @NotNull(message = "服务ID不能为空", groups = { EditGroup.class })
    private Long serviceId;
    /**
     * 服务名称
     */
    @NotBlank(message = "服务名称不能为空", groups = { EditGroup.class })
    private String servicesName;

    /**
     * 下单用户昵称
     */
    @NotBlank(message = "下单用户昵称不能为空", groups = { EditGroup.class })
    private String userName;

    /**
     * 订单金额
     */
    @NotNull(message = "订单金额不能为空", groups = { EditGroup.class })
    private BigDecimal price;

    /**
     * 订单备注
     */
    @NotBlank(message = "订单备注不能为空", groups = { EditGroup.class })
    private String remark;

    /**
     * 支付方式
     */
    private String paymentType;

    /**
     * 支付状态
     */
    @NotBlank(message = "支付状态不能为空", groups = { EditGroup.class })
    private String payStatus;
    /**
     * 预约地址
     */
    private String address;
    /**
     * 订单状态
     */
    @NotBlank(message = "订单状态不能为空", groups = { EditGroup.class })
    private String status;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 支付流水号
     */
    private String traceNo;
}
