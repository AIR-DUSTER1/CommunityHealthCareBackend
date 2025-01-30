package org.dromara.services.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 账户管理对象 ch_services_account
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ch_services_account")
public class ChServicesAccount extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账户ID
     */
    @TableId(value = "account_id")
    private Long accountId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 账户名称
     */
    @TableField(value = "account_name")
    private String accountName;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 冻结金额
     */
    @TableField(value = "freeze_balance")
    private BigDecimal freezeBalance;

    /**
     * 账户状态
     */
    private String status;

    /**
     * 账户密码
     */
    @TableField(value = "account_password")
    private String accountPassword;

    /**
     * 实名认证状态
     */
    @TableField(value = "real_name_status")
    private String realNameStatus;

    /**
     * 备注
     */
    private String remark;


}
