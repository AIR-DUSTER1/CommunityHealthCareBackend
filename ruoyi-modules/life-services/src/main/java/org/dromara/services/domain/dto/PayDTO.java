package org.dromara.services.domain.dto;

import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

@Data
public class PayDTO {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 账户id
     */
    private Long AccountId;
    /**
     * 服务id
     */
    private Long ServiceId;
    /**
     * 金额
     */
    private BigDecimal price;

}
