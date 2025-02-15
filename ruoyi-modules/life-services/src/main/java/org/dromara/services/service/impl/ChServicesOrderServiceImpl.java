package org.dromara.services.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.validation.constraints.NotBlank;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.services.domain.ChPayConfig;
import org.dromara.services.domain.ChServices;
import org.springframework.stereotype.Service;
import org.dromara.services.domain.bo.ChServicesOrderBo;
import org.dromara.services.domain.vo.ChServicesOrderVo;
import org.dromara.services.domain.ChServicesOrder;
import org.dromara.services.mapper.ChServicesOrderMapper;
import org.dromara.services.service.IChServicesOrderService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 服务订单Service业务层处理
 *
 * @author AIR-DUSTER1
 * @date 2024-12-24
 */
@RequiredArgsConstructor
@Service
public class ChServicesOrderServiceImpl implements IChServicesOrderService {

    private final ChServicesOrderMapper baseMapper;

    /**
     * 查询服务订单
     *
     * @param servicesOrderId 主键
     * @return 服务订单
     */
    @Override
    public ChServicesOrderVo queryById(Long servicesOrderId){
        return baseMapper.selectVoById(servicesOrderId);
    }

    /**
     * 分页查询服务订单列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 服务订单分页列表
     */
    @Override
    public TableDataInfo<ChServicesOrderVo> queryPageList(ChServicesOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ChServicesOrder> lqw = buildQueryWrapper(bo);
        Page<ChServicesOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的服务订单列表
     *
     * @param bo 查询条件
     * @return 服务订单列表
     */
    @Override
    public List<ChServicesOrderVo> queryList(ChServicesOrderBo bo) {
        LambdaQueryWrapper<ChServicesOrder> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChServicesOrder> buildQueryWrapper(ChServicesOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ChServicesOrder> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getServicesName()), ChServicesOrder::getServicesName, bo.getServicesName());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), ChServicesOrder::getUserName, bo.getUserName());
        lqw.between(params.get("beginTime") != null && params.get("endTime") != null,
            ChServicesOrder::getUpdateTime, params.get("beginTime"), params.get("endTime"));
        if (params.get("minPrice") != null && params.get("maxPrice") != null) {
            Object minPriceObj = params.get("minPrice");
            Object maxPriceObj = params.get("maxPrice");
            Long minPrice = Long.valueOf(minPriceObj.toString());
            Long maxPrice = Long.valueOf(maxPriceObj.toString());
            lqw.between(ChServicesOrder::getPrice , minPrice, maxPrice);
        }
        lqw.eq(StringUtils.isNotBlank(bo.getPaymentType()), ChServicesOrder::getPaymentType, bo.getPaymentType());
        lqw.eq(StringUtils.isNotBlank(bo.getPayStatus()), ChServicesOrder::getPayStatus, bo.getPayStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), ChServicesOrder::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增服务订单
     *
     * @param bo 服务订单
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(ChServicesOrderBo bo) {
        ChServicesOrder add = MapstructUtils.convert(bo, ChServicesOrder.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setServicesOrderId(add.getServicesOrderId());
        }
        return flag;
    }

    /**
     * 修改服务订单
     *
     * @param bo 服务订单
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(ChServicesOrderBo bo) {
        ChServicesOrder update = MapstructUtils.convert(bo, ChServicesOrder.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ChServicesOrder entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除服务订单信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    public int changeOrderStatus(ChServicesOrderBo bo) {
        return baseMapper.update(null, new LambdaUpdateWrapper<ChServicesOrder>()
            .set(StringUtils.isNotBlank(bo.getPayStatus()),ChServicesOrder::getPayStatus,bo.getPayStatus())
            .set(StringUtils.isNotBlank(bo.getStatus()),ChServicesOrder::getStatus, bo.getStatus()));
    }

}
