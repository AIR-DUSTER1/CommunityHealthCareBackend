package org.dromara.services.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.services.domain.bo.ChServicesStatusBo;
import org.dromara.services.domain.vo.Category;
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
import org.dromara.services.domain.vo.ChServicesVo;
import org.dromara.services.domain.bo.ChServicesBo;
import org.dromara.services.service.IChServicesService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 生活服务
 *
 * @author AIR-DUSTER1
 * @date 2024-12-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/services/livelihood")
public class ChServicesController extends BaseController {

    private final IChServicesService chServicesService;

    /**
     * 查询生活服务列表
     */
    @SaCheckPermission("services:services:list")
    @GetMapping("/list")
    public TableDataInfo<ChServicesVo> list(ChServicesBo bo, PageQuery pageQuery) {
        return chServicesService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出生活服务列表
     */
    @SaCheckPermission("services:services:export")
    @Log(title = "生活服务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ChServicesBo bo, HttpServletResponse response) {
        List<ChServicesVo> list = chServicesService.queryList(bo);
        ExcelUtil.exportExcel(list, "生活服务", ChServicesVo.class, response);
    }

    /**
     * 获取生活服务详细信息
     *
     * @param servicesId 主键
     */
    @SaCheckPermission("services:services:query")
    @GetMapping("/{servicesId}")
    public R<ChServicesVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long servicesId) {
        return R.ok(chServicesService.queryById(servicesId));
    }

    /**
     * 新增生活服务
     */
    @SaCheckPermission("services:services:add")
    @Log(title = "生活服务", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ChServicesBo bo) {
        return toAjax(chServicesService.insertByBo(bo));
    }

    /**
     * 修改生活服务
     */
    @SaCheckPermission("services:services:edit")
    @Log(title = "生活服务", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ChServicesBo bo) {
        return toAjax(chServicesService.updateByBo(bo));
    }

    /**
     * 删除生活服务
     *
     * @param servicesIds 主键串
     */
    @SaCheckPermission("services:services:remove")
    @Log(title = "生活服务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{servicesIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] servicesIds) {
        return toAjax(chServicesService.deleteWithValidByIds(List.of(servicesIds), true));
    }

    /**
     * 更新服务状态
     * @param bo
     * @return
     */
    @SaCheckPermission("services:services:edit")
    @Log(title = "生活服务", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/changeStatus")
    public R<Void> serviceChangeStatus(@RequestBody ChServicesStatusBo bo) {
        Boolean b = chServicesService.publishNews(bo.getStatus(), "上新了" + bo.getName() + "服务");
        if (!b&& bo.getStatus().equals("1")){
            return R.fail("服务状态更新失败");
        }
        return toAjax(chServicesService.updateStatus(bo));
    }

    /**
     * 服务分类查询
     * @param type
     * @return
     */
    @SaIgnore
    @GetMapping("/category")
    public R<List<Category>> queryCategory(String type){
        return R.ok(chServicesService.queryCategory(type));
    }

    /**
     * 查询全部
     * @param type
     * @return
     */
    @SaIgnore
    @GetMapping("/query/all")
    public R<List<Category>> queryAll(String type,String name){
        return R.ok(chServicesService.queryAll(type,name));
    }
}
