package org.dromara.services.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.services.domain.ChServicesOrder;
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
import org.dromara.services.domain.vo.ChServicesOrderVo;
import org.dromara.services.domain.bo.ChServicesOrderBo;
import org.dromara.services.service.IChServicesOrderService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 服务订单
 *
 * @author AIR-DUSTER1
 * @date 2024-12-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/services/servicesOrder")
public class ChServicesOrderController extends BaseController {

    private final IChServicesOrderService chServicesOrderService;

    /**
     * 查询服务订单列表
     */
    @SaCheckPermission("services:servicesOrder:list")
    @GetMapping("/list")
    public TableDataInfo<ChServicesOrderVo> list(ChServicesOrderBo bo, PageQuery pageQuery) {
        return chServicesOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出服务订单列表
     */
    @SaCheckPermission("services:servicesOrder:export")
    @Log(title = "服务订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ChServicesOrderBo bo, HttpServletResponse response) {
        List<ChServicesOrderVo> list = chServicesOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "服务订单", ChServicesOrderVo.class, response);
    }

    /**
     * 获取服务订单详细信息
     *
     * @param servicesOrderId 主键
     */
    @SaCheckPermission("services:servicesOrder:query")
    @GetMapping("/{servicesOrderId}")
    public R<ChServicesOrder> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String servicesOrderId) {

        return R.ok(chServicesOrderService.queryById(servicesOrderId));
    }

    /**
     * 新增服务订单
     */
//    @SaCheckPermission("services:servicesOrder:add")
//    @Log(title = "服务订单", businessType = BusinessType.INSERT)
//    @RepeatSubmit()
//    @PostMapping()
//    public R<Void> add(@Validated(AddGroup.class) @RequestBody ChServicesOrderBo bo) {
//        return toAjax(chServicesOrderService.insertByBo(bo));
//    }

    /**
     * 修改服务订单
     */
    @SaCheckPermission("services:servicesOrder:edit")
    @Log(title = "服务订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ChServicesOrderBo bo) {
        return toAjax(chServicesOrderService.updateByBo(bo));
    }

    /**
     * 删除服务订单
     *
     * @param servicesOrderIds 主键串
     */
    @SaCheckPermission("services:servicesOrder:remove")
    @Log(title = "服务订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{servicesOrderIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] servicesOrderIds) {
        return toAjax(chServicesOrderService.deleteWithValidByIds(List.of(servicesOrderIds), true));
    }
    @SaIgnore
    @GetMapping("/app/list")
    public TableDataInfo<ChServicesOrderVo> appList(ChServicesOrderBo bo, PageQuery pageQuery) {
        return chServicesOrderService.queryPageListApp(bo, pageQuery);
    }
}
