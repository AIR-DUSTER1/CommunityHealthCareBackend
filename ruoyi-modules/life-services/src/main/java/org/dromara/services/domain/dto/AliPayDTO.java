package org.dromara.services.domain.dto;

import lombok.Data;

@Data
public class AliPayDTO {
    /**
     * 总金额
     */
    private String totalAmount;
    /**
     *
     */
    private String cardNo;
}
