package org.dromara.services.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@ExcelIgnoreUnannotated
public class Category implements Serializable {
    private final static long serialVersionUID = 1L;
    private String name;
    private List<ServicesItemVo> services;
}
