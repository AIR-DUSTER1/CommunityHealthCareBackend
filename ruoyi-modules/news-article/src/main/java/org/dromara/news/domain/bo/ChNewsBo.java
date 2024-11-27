package org.dromara.news.domain.bo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.news.domain.ChNews;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 新闻信息业务对象 ch_news
 *
 * @author AIR-DUSTER1
 * @date 2024-11-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ChNews.class, reverseConvertGenerate = false)
public class ChNewsBo extends BaseEntity {

    /**
     * 新闻id
     */
    private Long newsId;

    /**
     * 新闻封面
     */
    @NotBlank(message = "新闻封面不能为空", groups = { AddGroup.class, EditGroup.class })
    @Translation(type = TransConstant.URl, mapper = "cover")
    private String cover;

    /**
     * 作者
     */
    @NotBlank(message = "作者不能为空", groups = { AddGroup.class, EditGroup.class })
    private String author;

    /**
     * 来源
     */
    @NotBlank(message = "来源不能为空", groups = { AddGroup.class, EditGroup.class })
    private String source;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;


}
