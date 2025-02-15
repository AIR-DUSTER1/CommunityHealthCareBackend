package org.dromara.services.service;

import org.dromara.services.domain.vo.ChPayConfigVo;
import org.dromara.services.domain.bo.ChPayConfigBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 支付宝配置管理Service接口
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
public interface IChPayConfigService {

    /**
     * 查询支付宝配置管理
     *
     * @param payId 主键
     * @return 支付宝配置管理
     */
    ChPayConfigVo queryById(Long payId);

    /**
     * 分页查询支付宝配置管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 支付宝配置管理分页列表
     */
    TableDataInfo<ChPayConfigVo> queryPageList(ChPayConfigBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的支付宝配置管理列表
     *
     * @param bo 查询条件
     * @return 支付宝配置管理列表
     */
    List<ChPayConfigVo> queryList(ChPayConfigBo bo);

    /**
     * 新增支付宝配置管理
     *
     * @param bo 支付宝配置管理
     * @return 是否新增成功
     */
    Boolean insertByBo(ChPayConfigBo bo);

    /**
     * 修改支付宝配置管理
     *
     * @param bo 支付宝配置管理
     * @return 是否修改成功
     */
    Boolean updateByBo(ChPayConfigBo bo);

    /**
     * 校验并批量删除支付宝配置管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    int updatePayConfigStatus(ChPayConfigBo bo);
}
