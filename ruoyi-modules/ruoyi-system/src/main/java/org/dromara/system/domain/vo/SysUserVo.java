package org.dromara.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.common.sensitive.annotation.Sensitive;
import org.dromara.common.sensitive.core.SensitiveStrategy;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.system.domain.SysUser;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 用户信息视图对象 sys_user
 *
 * @author AirDuster1
 * @date 2025-03-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysUser.class)
public class SysUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;
    /**
     * 部门ID
     */
    @ExcelProperty(value = "部门ID")
    private Long deptId;

    /**
     * 用户账号
     */
    @ExcelProperty(value = "用户账号")
    private String userName;

    /**
     * 用户昵称
     */
    @ExcelProperty(value = "用户昵称")
    private String nickName;

    /**
     * 偏好服务时间
     */
    @ExcelProperty(value = "偏好服务时间")
    private String preferredServiceTime;

    /**
     * 用户类型（sys_user系统用户）
     */
    @ExcelProperty(value = "用户类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_type")
    private String userType;

    /**
     * 是否独居
     */
    @ExcelProperty(value = "是否独居", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_alone")
    private String livingAlone;

    /**
     * 婚姻状况
     */
    @ExcelProperty(value = "婚姻状况", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_marital")
    private String maritalStatus;

    /**
     * 紧急联系人
     */
    @ExcelProperty(value = "紧急联系人")
    private String emergencyPhone;

    /**
     * 慢性病信息
     */
    @ExcelProperty(value = "慢性病信息")
    private String chronicDiseases;

    /**
     * 健康状况
     */
    @ExcelProperty(value = "健康状况", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ch_services_account_physical_condition")
    private String healthStatus;

    /**
     * 详细地址
     */
    @ExcelProperty(value = "详细地址")
    private String address;

    /**
     * 用户邮箱
     */
    @ExcelProperty(value = "用户邮箱")
    @Sensitive(strategy = SensitiveStrategy.EMAIL, perms = "system:user:edit")
    private String email;

    /**
     * 手机号码
     */
    @ExcelProperty(value = "手机号码")
    @Sensitive(strategy = SensitiveStrategy.PHONE, perms = "system:user:edit")
    private String phoneNumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @ExcelProperty(value = "用户性别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private String sex;

    /**
     * 头像地址
     */
    @ExcelProperty(value = "头像地址")
    @Translation(type = TransConstant.OSS_ID_TO_URL)
    private Long avatar;

    /**
     * 年龄
     */
    @ExcelProperty(value = "年龄")
    private Long age;

    /**
     * 出生日期
     */
    @ExcelProperty(value = "出生日期")
    private Date birthDate;

    /**
     * 密码
     */
    @ExcelProperty(value = "密码")
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ExcelProperty(value = "帐号状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_status")
    private String status;

    /**
     * 最后登录IP
     */
    @ExcelProperty(value = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @ExcelProperty(value = "最后登录时间")
    private Date loginDate;

    /**
     * 创建部门
     */
    @ExcelProperty(value = "创建部门")
    @Translation(type = TransConstant.DEPT_ID_TO_NAME)
    private Long createDept;

    /**
     * 创建者
     */
    @ExcelProperty(value = "创建者")
    @Translation(type = TransConstant.USER_ID_TO_NICKNAME)
    private Long createBy;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新者
     */
    @ExcelProperty(value = "更新者")
    @Translation(type = TransConstant.USER_ID_TO_NICKNAME)
    private Long updateBy;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;
    /**
     * 角色对象
     */
    private List<SysRoleVo> roles;
    /**
     * 角色组
     */
    private Long[] roleIds;

    /**
     * 岗位组
     */
    private Long[] postIds;

    /**
     * 数据权限 当前角色ID
     */
    private Long roleId;

}
