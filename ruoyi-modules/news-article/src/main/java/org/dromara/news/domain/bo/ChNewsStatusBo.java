package org.dromara.news.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Data
public class ChNewsStatusBo implements Serializable {
    /**
     * 新闻id
     */
    @NotBlank( message = "新闻id不能为空")
    private Long newsId;
    /**
     * 新闻标题
     */
    @NotBlank(message = "新闻标题不能为空")
    private String title;
    /**
     * 新闻状态
     */
    private String status;
}
