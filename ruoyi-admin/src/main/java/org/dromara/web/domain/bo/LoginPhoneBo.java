package org.dromara.web.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
@Data
public class LoginPhoneBo implements Serializable {
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
    /**
     * 授权类型
     */
    @NotBlank(message = "{auth.grant.type.not.blank}")
    private String grantType;
    /**
     * 客户端id
     */
    @NotBlank(message = "{auth.clientid.not.blank}")
    private String clientId;
}
