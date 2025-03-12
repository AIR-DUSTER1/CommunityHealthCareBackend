package org.dromara.services.service;

import org.dromara.services.domain.dto.PayOrderDTO;
import org.dromara.services.domain.vo.ChServicesOrderVo;
import org.dromara.services.domain.bo.ChServicesOrderBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 服务订单Service接口
 *
 * @author AIR-DUSTER1
 * @date 2024-12-24
 */
public interface IChServicesOrderService {

    /**
     * 查询服务订单
     *
     * @param servicesOrderId 主键
     * @return 服务订单
     */
    ChServicesOrderVo queryById(String servicesOrderId);

    /**
     * 分页查询服务订单列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 服务订单分页列表
     */
    TableDataInfo<ChServicesOrderVo> queryPageList(ChServicesOrderBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的服务订单列表
     *
     * @param bo 查询条件
     * @return 服务订单列表
     */
    List<ChServicesOrderVo> queryList(ChServicesOrderBo bo);

    /**
     * 新增服务订单
     *
     * @param bo 服务订单
     * @return 是否新增成功
     */
    Boolean insertByBo(ChServicesOrderBo bo);

    /**
     * 修改服务订单
     *
     * @param bo 服务订单
     * @return 是否修改成功
     */
    Boolean updateByBo(ChServicesOrderBo bo);

    /**
     * 校验并批量删除服务订单信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    int changeOrderStatus(ChServicesOrderBo bo);
}
