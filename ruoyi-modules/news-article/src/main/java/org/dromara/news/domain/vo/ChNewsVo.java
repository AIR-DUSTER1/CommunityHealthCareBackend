package org.dromara.news.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.news.domain.ChNews;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 新闻信息视图对象 ch_news
 *
 * @author AIR-DUSTER1
 * @date 2024-11-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ChNews.class)
public class ChNewsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 新闻id
     */
    @ExcelProperty(value = "新闻id")
    private Long newsId;

    /**
     * 新闻封面
     */
    @ExcelProperty(value = "新闻封面")
    private String cover;
    /**
     * 作者
     */
    @ExcelProperty(value = "作者")
    private String author;

    /**
     * 来源
     */
    @ExcelProperty(value = "来源")
    private String source;

    /**
     * 标题
     */
    @ExcelProperty(value = "标题")
    private String title;

    /**
     * 内容
     */
    @ExcelProperty(value = "内容")
    private String content;
    /**
     * 新闻标记
     */
    private String slug;
    /**
     * 浏览量
     */
    @ExcelProperty(value = "浏览量")
    private Long views;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ch_news_status")
    private String status;

    /**
     * 创建部门
     */
    @ExcelProperty(value = "创建部门")
    @Translation(type = TransConstant.DEPT_ID_TO_NAME,mapper = "createDept")
    private Long createDept;

    /**
     * 创建者
     */
    @ExcelProperty(value = "创建者")
    @Translation(type = TransConstant.USER_ID_TO_NAME,mapper = "createBy")
    private Long createBy;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

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
