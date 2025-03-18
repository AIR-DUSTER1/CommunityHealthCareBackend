package org.dromara.services.domain.dto;

import lombok.Data;

@Data
public class BuyDTO {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 服务id
     */
    private Long serviceId;
    /**
     * 账号id
     */
    private Long accountId;
    /**
     * 下单手机号
     */
    private String phone;
    /**
     * 下单详细地址
     */
    private String address;
}
