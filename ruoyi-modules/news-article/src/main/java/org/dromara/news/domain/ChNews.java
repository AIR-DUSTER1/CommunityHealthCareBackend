package org.dromara.news.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 新闻信息对象 ch_news
 *
 * @author AIR-DUSTER1
 * @date 2024-11-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ch_news")
public class ChNews extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 新闻id
     */
    @TableId(value = "news_id")
    private Long newsId;

    /**
     * 新闻封面
     */
    private String cover;

    /**
     * 作者
     */
    private String author;

    /**
     * 来源
     */
    private String source;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 浏览量
     */
    private Long views = 0L;

    /**
     * 状态
     */
    private Long status;


}
