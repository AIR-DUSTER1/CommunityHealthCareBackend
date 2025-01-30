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
}
