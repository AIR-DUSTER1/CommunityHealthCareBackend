package org.dromara.services.controller;

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
import org.dromara.services.domain.vo.ChPayConfigVo;
import org.dromara.services.domain.bo.ChPayConfigBo;
import org.dromara.services.service.IChPayConfigService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 支付宝配置管理
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/services/payConfig")
public class ChPayConfigController extends BaseController {

    private final IChPayConfigService chPayConfigService;

    /**
     * 查询支付宝配置管理列表
     */
    @SaCheckPermission("services:payConfig:list")
    @GetMapping("/list")
    public TableDataInfo<ChPayConfigVo> list(ChPayConfigBo bo, PageQuery pageQuery) {
        return chPayConfigService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出支付宝配置管理列表
     */
    @SaCheckPermission("services:payConfig:export")
    @Log(title = "支付宝配置管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ChPayConfigBo bo, HttpServletResponse response) {
        List<ChPayConfigVo> list = chPayConfigService.queryList(bo);
        ExcelUtil.exportExcel(list, "支付宝配置管理", ChPayConfigVo.class, response);
    }

    /**
     * 获取支付宝配置管理详细信息
     *
     * @param payId 主键
     */
    @SaCheckPermission("services:payConfig:query")
    @GetMapping("/{payId}")
    public R<ChPayConfigVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long payId) {
        return R.ok(chPayConfigService.queryById(payId));
    }

    /**
     * 新增支付宝配置管理
     */
    @SaCheckPermission("services:payConfig:add")
    @Log(title = "支付宝配置管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ChPayConfigBo bo) {
        return toAjax(chPayConfigService.insertByBo(bo));
    }

    /**
     * 修改支付宝配置管理
     */
    @SaCheckPermission("services:payConfig:edit")
    @Log(title = "支付宝配置管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ChPayConfigBo bo) {
        return toAjax(chPayConfigService.updateByBo(bo));
    }

    /**
     * 删除支付宝配置管理
     *
     * @param payIds 主键串
     */
    @SaCheckPermission("services:payConfig:remove")
    @Log(title = "支付宝配置管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{payIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] payIds) {
        return toAjax(chPayConfigService.deleteWithValidByIds(List.of(payIds), true));
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("services:payConfig:edit")
    @Log(title = "支付宝配置管理状态修改", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody ChPayConfigBo bo) {
        return toAjax(chPayConfigService.updatePayConfigStatus(bo));
    }
}
