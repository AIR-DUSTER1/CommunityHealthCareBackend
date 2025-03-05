package org.dromara.services.domain.bo;

import org.dromara.services.domain.ChServicesAccount;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 账户管理业务对象 ch_services_account
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ChServicesAccount.class, reverseConvertGenerate = false)
public class ChServicesAccountBo extends BaseEntity {

    /**
     * 账户ID
     */
    @NotNull(message = "账户ID不能为空", groups = { EditGroup.class })
    private Long accountId;

    /**
     * 账户名称
     */
    @NotBlank(message = "账户名称不能为空", groups = { EditGroup.class })
    private String accountName;
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;
    /**
     * 账户余额
     */
    private Long balance = 100L;

    /**
     * 冻结金额
     */
    private Long freezeBalance = 0L;
    /**
     * 身体状态
     */
    private String physicalCondition;
    /**
     * 账户状态
     */
    @NotBlank(message = "账户状态不能为空", groups = { EditGroup.class })
    private String status;
    /**
     * 账户密码
     */
    private String accountPassword;
    /**
     * 实名状态
     */
    private String realNameStatus;
    /**
     * 备注
     */
    private String remark;


}
