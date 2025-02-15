package org.dromara.services.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.oss.constant.OssConstant;
import org.dromara.common.redis.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.dromara.services.domain.bo.ChPayConfigBo;
import org.dromara.services.domain.vo.ChPayConfigVo;
import org.dromara.services.domain.ChPayConfig;
import org.dromara.services.mapper.ChPayConfigMapper;
import org.dromara.services.service.IChPayConfigService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 支付宝配置管理Service业务层处理
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
@RequiredArgsConstructor
@Service
public class ChPayConfigServiceImpl implements IChPayConfigService {

    private final ChPayConfigMapper baseMapper;

    /**
     * 查询支付宝配置管理
     *
     * @param payId 主键
     * @return 支付宝配置管理
     */
    @Override
    public ChPayConfigVo queryById(Long payId){
        return baseMapper.selectVoById(payId);
    }

    /**
     * 分页查询支付宝配置管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 支付宝配置管理分页列表
     */
    @Override
    public TableDataInfo<ChPayConfigVo> queryPageList(ChPayConfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ChPayConfig> lqw = buildQueryWrapper(bo);
        Page<ChPayConfigVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的支付宝配置管理列表
     *
     * @param bo 查询条件
     * @return 支付宝配置管理列表
     */
    @Override
    public List<ChPayConfigVo> queryList(ChPayConfigBo bo) {
        LambdaQueryWrapper<ChPayConfig> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChPayConfig> buildQueryWrapper(ChPayConfigBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ChPayConfig> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), ChPayConfig::getStatus, bo.getStatus());
        lqw.like(StringUtils.isNotBlank(bo.getRemark()), ChPayConfig::getRemark, bo.getRemark());
        return lqw;
    }

    /**
     * 新增支付宝配置管理
     *
     * @param bo 支付宝配置管理
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(ChPayConfigBo bo) {
        ChPayConfig add = MapstructUtils.convert(bo, ChPayConfig.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPayId(add.getPayId());
        }
        return flag;
    }

    /**
     * 修改支付宝配置管理
     *
     * @param bo 支付宝配置管理
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(ChPayConfigBo bo) {
        ChPayConfig update = MapstructUtils.convert(bo, ChPayConfig.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ChPayConfig entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除支付宝配置管理信息
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
    public int updatePayConfigStatus(ChPayConfigBo bo) {
        ChPayConfig chPayConfig = MapstructUtils.convert(bo, ChPayConfig.class);
        int row = baseMapper.update(null, new LambdaUpdateWrapper<ChPayConfig>()
            .set(ChPayConfig::getStatus, "1"));
        row += baseMapper.updateById(chPayConfig);
//        if (row > 0) {
//            RedisUtils.setCacheObject(OssConstant.DEFAULT_CONFIG_KEY, chPayConfig.getConfigKey());
//        }
        return row;
    }
}
