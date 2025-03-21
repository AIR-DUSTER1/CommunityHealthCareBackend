package org.dromara.system.domain;

import lombok.Data;

@Data
public class SysUerOderInfo {
    /**
     * 用户账号
     */
    private String nickName;
    /**
     * 手机号码
     */
    private String phoneNumber;
    /**
     * 居住地址
     */
    private String address;

}
