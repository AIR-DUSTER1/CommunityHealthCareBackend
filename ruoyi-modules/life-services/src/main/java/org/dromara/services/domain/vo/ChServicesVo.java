package org.dromara.services.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.services.domain.ChServices;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 生活服务视图对象 ch_services
 *
 * @author AIR-DUSTER1
 * @date 2024-12-13
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ChServices.class)
public class ChServicesVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 服务ID
     */
    @ExcelProperty(value = "服务ID")
    private Long servicesId;

    /**
     * 服务名称
     */
    @ExcelProperty(value = "服务名称")
    private String name;

    /**
     * 图标路径
     */
    @ExcelProperty(value = "图标路径")
    private String icon;

    /**
     * 服务描述
     */
    @ExcelProperty(value = "服务描述")
    private String description;

    /**
     * 分类
     */
    @ExcelProperty(value = "分类")
    @ExcelDictFormat(dictType = "ch_services_classification")
    private String classification;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ch_services_type")
    private String type;

    /**
     * 价格
     */
    @ExcelProperty(value = "价格")
    private BigDecimal price;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ch_services_status")
    private String status;
    /**
     * 创建部门
     */
    @ExcelProperty(value = "创建部门")
    @Translation(type = TransConstant.DEPT_ID_TO_NAME,mapper = "createDept")
    private Long createDept;
    /**
     * 更新者
     */
    @ExcelProperty(value = "更新者")
    @Translation(type = TransConstant.USER_ID_TO_NAME,mapper = "updateBy")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updateTime;


}
