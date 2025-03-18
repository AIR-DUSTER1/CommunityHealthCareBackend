package org.dromara.services.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.services.domain.ChServicesOrder;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 服务订单视图对象 ch_services_order
 *
 * @author AIR-DUSTER1
 * @date 2024-12-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ChServicesOrder.class)
public class ChServicesOrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 服务订单ID
     */
    @ExcelProperty(value = "服务订单ID")
    private String servicesOrderId;
    /**
     * 服务ID
     */
    @ExcelProperty(value = "服务ID")
    private Long serviceId;
    /**
     * 服务名称
     */
    @ExcelProperty(value = "服务名称")
    private String servicesName;
    /**
     * 下单用户ID
     */
    @ExcelProperty(value = "下单用户ID")
    private Long UserId;
    /**
     * 下单用户昵称
     */
    @ExcelProperty(value = "下单用户昵称")
    private String userName;

    /**
     * 订单金额
     */
    @ExcelProperty(value = "订单金额")
    private BigDecimal price;
    /**
     * 预约电话
     */
    private String phone;
    /**
     * 预约地址
     */
    private String address;
    /**
     * 订单备注
     */
    @ExcelProperty(value = "订单备注")
    private String remark;

    /**
     * 支付方式
     */
    @ExcelProperty(value = "支付方式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ch_services_pay_type")
    private String paymentType;

    /**
     * 支付状态
     */
    @ExcelProperty(value = "支付状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ch_serveices_order_pay_status")
    private String payStatus;

    /**
     * 订单状态
     */
    @ExcelProperty(value = "订单状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ch_services_order_status")
    private String status;

    /**
     * 支付流水号
     */
    @ExcelProperty(value = "支付流水号")
    private String traceNo;
    /**
     * 创建部门
     */
    @ExcelProperty(value = "创建部门")
    @Translation(type = TransConstant.DEPT_ID_TO_NAME,mapper = "createDept")
    private Long createDept;
    /**
     * 更新者
     */
    @ExcelProperty(value = "更新者")
    @Translation(type = TransConstant.USER_ID_TO_NAME,mapper = "updateBy")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updateTime;

}
