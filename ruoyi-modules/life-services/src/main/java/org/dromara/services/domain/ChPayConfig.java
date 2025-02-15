package org.dromara.services.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 支付宝配置管理对象 ch_pay_config
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ch_pay_config")
public class ChPayConfig extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "pay_id")
    private Long payId;

    /**
     * 支付宝APPID
     */
    private String appId;

    /**
     * 支付宝私钥
     */
    private String privateKey;

    /**
     * 支付宝公钥
     */
    private String publicKey;

    /**
     * 异步回调地址
     */
    @TableField(value = "notify_url")
    private String notifyUrl;

    /**
     * 同步回调地址
     */
    @TableField(value = "return_url")
    private String returnUrl;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;


}
