package org.dromara.services.domain.bo;

import org.dromara.services.domain.ChPayConfig;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 支付宝配置管理业务对象 ch_pay_config
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ChPayConfig.class, reverseConvertGenerate = false)
public class ChPayConfigBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long payId;

    /**
     * 支付宝APPID
     */
    @NotBlank(message = "支付宝APPID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appId;

    /**
     * 支付宝私钥
     */
    @NotBlank(message = "支付宝私钥不能为空", groups = { AddGroup.class, EditGroup.class })
    private String privateKey;

    /**
     * 支付宝公钥
     */
    @NotBlank(message = "支付宝公钥不能为空", groups = { AddGroup.class, EditGroup.class })
    private String publicKey;

    /**
     * 应用回调地址
     */
    @NotBlank(message = "应用回调地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String notifyUrl;

    /**
     * 同步回调地址
     */
    private String returnUrl;

    /**
     * 状态
     */
    @NotBlank(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 备注
     */
    private String remark;


}
