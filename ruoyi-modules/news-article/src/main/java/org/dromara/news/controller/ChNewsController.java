package org.dromara.news.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.news.domain.vo.ChNewsVo;
import org.dromara.news.domain.bo.ChNewsBo;
import org.dromara.news.service.IChNewsService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 新闻信息
 *
 * @author AIR-DUSTER1
 * @date 2024-11-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/news/news")
public class ChNewsController extends BaseController {

    private final IChNewsService chNewsService;

    /**
     * 查询新闻信息列表
     */
    @SaCheckPermission("news:news:list")
    @GetMapping("/list")
    public TableDataInfo<ChNewsVo> list(ChNewsBo bo, PageQuery pageQuery) {
        return chNewsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出新闻信息列表
     */
    @SaCheckPermission("news:news:export")
    @Log(title = "新闻信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ChNewsBo bo, HttpServletResponse response) {
        List<ChNewsVo> list = chNewsService.queryList(bo);
        ExcelUtil.exportExcel(list, "新闻信息", ChNewsVo.class, response);
    }

    /**
     * 获取新闻信息详细信息
     *
     * @param newsId 主键
     */
    @SaCheckPermission("news:news:query")
    @GetMapping("/{newsId}")
    public R<ChNewsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long newsId) {
        return R.ok(chNewsService.queryById(newsId));
    }

    /**
     * 新增新闻信息
     */
    @SaCheckPermission("news:news:add")
    @Log(title = "新闻信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ChNewsBo bo) {
        return toAjax(chNewsService.insertByBo(bo));
    }

    /**
     * 修改新闻信息
     */
    @SaCheckPermission("news:news:edit")
    @Log(title = "新闻信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ChNewsBo bo) {
        return toAjax(chNewsService.updateByBo(bo));
    }

    /**
     * 删除新闻信息
     *
     * @param newsIds 主键串
     */
    @SaCheckPermission("news:news:remove")
    @Log(title = "新闻信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{newsIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] newsIds) {
        return toAjax(chNewsService.deleteWithValidByIds(List.of(newsIds), true));
    }
}
