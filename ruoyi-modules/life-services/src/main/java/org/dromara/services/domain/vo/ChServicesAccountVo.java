package org.dromara.services.domain.vo;

import org.dromara.services.domain.ChServicesAccount;
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
 * 账户管理视图对象 ch_services_account
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ChServicesAccount.class)
public class ChServicesAccountVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账户ID
     */
    @ExcelProperty(value = "账户ID")
    private Long accountId;

    /**
     * 账户名称
     */
    @ExcelProperty(value = "账户名称")
    private String accountName;
    /**
     * 用户id
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 账户余额
     */
    @ExcelProperty(value = "账户余额")
    private Long balance;

    /**
     * 冻结金额
     */
    @ExcelProperty(value = "冻结金额")
    private Long freezeBalance;
    /**
     * 身体状态
     */
    @ExcelProperty(value = "账户状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ch_services_account_physical_condition")
    private String physicalCondition;
    /**
     * 账户状态
     */
    @ExcelProperty(value = "账户状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ch_services_account_status")
    private String status;

    /**
     * 实名认证状态
     */
    @ExcelProperty(value = "实名认证状态")
    private String realNameStatus;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
