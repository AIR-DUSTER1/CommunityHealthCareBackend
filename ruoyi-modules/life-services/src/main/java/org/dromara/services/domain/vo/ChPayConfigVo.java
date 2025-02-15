package org.dromara.services.domain.vo;

import org.dromara.services.domain.ChPayConfig;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 支付宝配置管理视图对象 ch_pay_config
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ChPayConfig.class)
public class ChPayConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long payId;

    /**
     * 支付宝APPID
     */
    @ExcelProperty(value = "支付宝APPID")
    private String appId;

    /**
     * 支付宝私钥
     */
    @ExcelProperty(value = "支付宝私钥")
    private String privateKey;

    /**
     * 支付宝公钥
     */
    @ExcelProperty(value = "支付宝公钥")
    private String publicKey;

    /**
     * 应用回调地址
     */
    @ExcelProperty(value = "应用回调地址")
    private String notifyUrl;

    /**
     * 同步回调地址
     */
    @ExcelProperty(value = "同步回调地址")
    private String returnUrl;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ch_service_pay_config_status")
    private String status;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
