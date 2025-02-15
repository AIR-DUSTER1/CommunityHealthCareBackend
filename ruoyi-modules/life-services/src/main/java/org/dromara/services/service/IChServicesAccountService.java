package org.dromara.services.service;

import org.dromara.services.domain.vo.ChServicesAccountVo;
import org.dromara.services.domain.bo.ChServicesAccountBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 账户管理Service接口
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
public interface IChServicesAccountService {

    /**
     * 查询账户管理
     *
     * @param accountId 主键
     * @return 账户管理
     */
    ChServicesAccountVo queryById(Long accountId);

    /**
     * 分页查询账户管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 账户管理分页列表
     */
    TableDataInfo<ChServicesAccountVo> queryPageList(ChServicesAccountBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的账户管理列表
     *
     * @param bo 查询条件
     * @return 账户管理列表
     */
    List<ChServicesAccountVo> queryList(ChServicesAccountBo bo);

    /**
     * 新增账户管理
     *
     * @param bo 账户管理
     * @return 是否新增成功
     */
    Boolean insertByBo(ChServicesAccountBo bo);

    /**
     * 修改账户管理
     *
     * @param bo 账户管理
     * @return 是否修改成功
     */
    Boolean updateByBo(ChServicesAccountBo bo);

    /**
     * 校验并批量删除账户管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
