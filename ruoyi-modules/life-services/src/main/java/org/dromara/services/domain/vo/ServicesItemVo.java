package org.dromara.services.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ExcelIgnoreUnannotated
public class ServicesItemVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long servicesId;
    private String name;
    private String key;
    private String icon;
    private BigDecimal price;
}
