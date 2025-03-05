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
import org.dromara.services.domain.vo.ChServicesAccountVo;
import org.dromara.services.domain.bo.ChServicesAccountBo;
import org.dromara.services.service.IChServicesAccountService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 账户管理
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/services/account")
public class ChServicesAccountController extends BaseController {

    private final IChServicesAccountService chServicesAccountService;

    /**
     * 查询账户管理列表
     */
    @SaCheckPermission("services:servicesAccount:list")
    @GetMapping("/list")
    public TableDataInfo<ChServicesAccountVo> list(ChServicesAccountBo bo, PageQuery pageQuery) {
        return chServicesAccountService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出账户管理列表
     */
    @SaCheckPermission("services:servicesAccount:export")
    @Log(title = "账户管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ChServicesAccountBo bo, HttpServletResponse response) {
        List<ChServicesAccountVo> list = chServicesAccountService.queryList(bo);
        ExcelUtil.exportExcel(list, "账户管理", ChServicesAccountVo.class, response);
    }

    /**
     * 获取账户管理详细信息
     *
     * @param accountId 主键
     */
    @SaCheckPermission("services:servicesAccount:query")
    @GetMapping("/{accountId}")
    public R<ChServicesAccountVo> getInfo(@NotNull(message = "主键不能为空")
                                          @PathVariable Long accountId) {
        return R.ok(chServicesAccountService.queryById(accountId));
    }

    /**
     * 新增账户管理
     */
    @SaCheckPermission("services:servicesAccount:add")
    @Log(title = "账户管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ChServicesAccountBo bo) {
        return toAjax(chServicesAccountService.insertByBo(bo));
    }

    /**
     * 修改账户管理
     */
    @SaCheckPermission("services:servicesAccount:edit")
    @Log(title = "账户管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ChServicesAccountBo bo) {
        return toAjax(chServicesAccountService.updateByBo(bo));
    }

    /**
     * 删除账户管理
     *
     * @param accountIds 主键串
     */
    @SaCheckPermission("services:servicesAccount:remove")
    @Log(title = "账户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{accountIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] accountIds) {
        return toAjax(chServicesAccountService.deleteWithValidByIds(List.of(accountIds), true));
    }

    /**
     * 根据用户id获取账户id
     * @param userId
     * @return
     */
    @GetMapping("/getAccountIdByUserId")
    public R<Long> getAccountIdByUserId(Long userId) {
        return R.ok(chServicesAccountService.getAccountIdByUserId(userId));
    }
}
