package org.dromara.system.domain.bo;

import org.dromara.common.core.constant.UserConstants;
import org.dromara.system.domain.SysUser;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户信息业务对象 sys_user
 *
 * @author AirDuster1
 * @date 2025-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysUser.class, reverseConvertGenerate = false)
public class SysUserBo extends BaseEntity {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { EditGroup.class })
    private Long userId;

    /**
     * 部门ID
     */
    @NotNull(message = "部门ID不能为空", groups = { EditGroup.class })
    private Long deptId;

    /**
     * 用户账号
     */
    @NotBlank(message = "用户账号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nickName;

    /**
     * 偏好服务时间
     */
    @NotBlank(message = "偏好服务时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String preferredServiceTime;

    /**
     * 用户类型（sys_user系统用户）
     */
    @NotBlank(message = "用户类型（sys_user系统用户）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userType;

    /**
     * 是否独居
     */
    @NotBlank(message = "是否独居不能为空", groups = { AddGroup.class, EditGroup.class })
    private String livingAlone;

    /**
     * 婚姻状况
     */
    @NotBlank(message = "婚姻状况不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maritalStatus;

    /**
     * 紧急联系人
     */
    @NotBlank(message = "紧急联系人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String emergencyPhone;

    /**
     * 慢性病信息
     */
    @NotBlank(message = "慢性病信息不能为空", groups = { AddGroup.class, EditGroup.class })
    private String chronicDiseases;

    /**
     * 健康状况
     */
    @NotBlank(message = "健康状况不能为空", groups = { AddGroup.class, EditGroup.class })
    private String healthStatus;

    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String address;

    /**
     * 用户邮箱
     */
    @NotBlank(message = "用户邮箱不能为空", groups = { AddGroup.class, EditGroup.class })
    private String email;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phoneNumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @NotBlank(message = "用户性别（0男 1女 2未知）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sex;

    /**
     * 头像地址
     */
    @NotNull(message = "头像地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long avatar;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long age;

    /**
     * 出生日期
     */
    @NotNull(message = "出生日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date birthDate;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @NotBlank(message = "帐号状态（0正常 1停用）不能为空", groups = { EditGroup.class })
    private String status;

    /**
     * 备注
     */
    private String remark;
    /**
     * 角色组
     */
    @Size(min = 1, message = "用户角色不能为空")
    private Long[] roleIds;

    /**
     * 岗位组
     */
    private Long[] postIds;
    /**
     * 数据权限 当前角色ID
     */
    private Long roleId;

    /**
     * 排除不查询的用户(工作流用)
     */
    private String excludeUserIds;
    public SysUserBo() {
    }
    public SysUserBo(Long userId) {
        this.userId = userId;
    }
    public boolean isSuperAdmin() {
        return UserConstants.SUPER_ADMIN_ID.equals(this.userId);
    }
}
