package org.dromara.services.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PayOrderDTO {
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 支付方式
     */
    private String payType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;
    private Integer payStatus;
    private String cancelReason;
    private String traceNo;
}
