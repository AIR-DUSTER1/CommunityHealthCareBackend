package org.dromara.system.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserPhoneBo implements Serializable {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phonenumber;

    /**
     * 手机验证码
     */
    @NotBlank(message = "手机验证码不能为空")
    private String smsCode;
}
