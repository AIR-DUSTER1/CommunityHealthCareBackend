package org.dromara.system.domain;

import org.dromara.common.core.constant.UserConstants;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 用户信息对象 sys_user
 *
 * @author AirDuster1
 * @date 2025-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 偏好服务时间
     */
    private String preferredServiceTime;

    /**
     * 用户类型（sys_user系统用户）
     */
    private String userType;

    /**
     * 是否独居
     */
    private String livingAlone;

    /**
     * 婚姻状况
     */
    private String maritalStatus;

    /**
     * 紧急联系人
     */
    private String emergencyPhone;

    /**
     * 慢性病信息
     */
    private String chronicDiseases;

    /**
     * 健康状况
     */
    private String healthStatus;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像地址
     */
    private Long avatar;

    /**
     * 年龄
     */
    private Long age;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    /**
     * 密码
     */
    @TableField(
        insertStrategy = FieldStrategy.NOT_EMPTY,
        updateStrategy = FieldStrategy.NOT_EMPTY,
        whereStrategy = FieldStrategy.NOT_EMPTY
    )
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 备注
     */
    private String remark;
    public SysUser() {
    }
    public SysUser(Long userId) {
        this.userId = userId;
    }

    public boolean isSuperAdmin() {
        return UserConstants.SUPER_ADMIN_ID.equals(this.userId);
    }
}
