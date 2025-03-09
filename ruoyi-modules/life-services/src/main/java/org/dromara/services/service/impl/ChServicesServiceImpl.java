package org.dromara.services.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.sse.dto.SseMessageDto;
import org.dromara.common.sse.utils.SseMessageUtils;
import org.dromara.services.domain.bo.ChServicesStatusBo;
import org.dromara.services.domain.vo.Category;
import org.dromara.services.domain.vo.ServicesItemVo;
import org.springframework.stereotype.Service;
import org.dromara.services.domain.bo.ChServicesBo;
import org.dromara.services.domain.vo.ChServicesVo;
import org.dromara.services.domain.ChServices;
import org.dromara.services.mapper.ChServicesMapper;
import org.dromara.services.service.IChServicesService;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 生活服务Service业务层处理
 *
 * @author AIR-DUSTER1
 * @date 2024-12-13
 */
@RequiredArgsConstructor
@Service
public class ChServicesServiceImpl implements IChServicesService {

    private final ChServicesMapper baseMapper;
    private final ScheduledExecutorService scheduledExecutorService;
    /**
     * 查询生活服务
     *
     * @param servicesId 主键
     * @return 生活服务
     */
    @Override
    public ChServicesVo queryById(Long servicesId){
        return baseMapper.selectVoById(servicesId);
    }

    /**
     * 分页查询生活服务列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 生活服务分页列表
     */
    @Override
    public TableDataInfo<ChServicesVo> queryPageList(ChServicesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ChServices> lqw = buildQueryWrapper(bo);
        Page<ChServicesVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的生活服务列表
     *
     * @param bo 查询条件
     * @return 生活服务列表
     */
    @Override
    public List<ChServicesVo> queryList(ChServicesBo bo) {
        LambdaQueryWrapper<ChServices> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChServices> buildQueryWrapper(ChServicesBo bo) {
        Map<String, Object> params = bo.getParams();

        LambdaQueryWrapper<ChServices> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), ChServices::getName, bo.getName());
        lqw.eq(bo.getClassification() != null, ChServices::getClassification, bo.getClassification());
        lqw.eq(bo.getType() != null, ChServices::getType, bo.getType());
        lqw.between(params.get("beginTime") != null && params.get("endTime") != null,
            ChServices::getUpdateTime, params.get("beginTime"), params.get("endTime"));
        if (params.get("minPrice") != null && params.get("maxPrice") != null) {
            Object minPriceObj = params.get("minPrice");
            Object maxPriceObj = params.get("maxPrice");
            Long minPrice = Long.valueOf(minPriceObj.toString());
            Long maxPrice = Long.valueOf(maxPriceObj.toString());
            lqw.between(ChServices::getPrice , minPrice, maxPrice);
        }
        lqw.eq(bo.getStatus() != null, ChServices::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增生活服务
     *
     * @param bo 生活服务
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(ChServicesBo bo) {
        ChServices add = MapstructUtils.convert(bo, ChServices.class);
        add.setStatus("0");
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setServicesId(add.getServicesId());
        }
        return flag;
    }

    /**
     * 修改生活服务
     *
     * @param bo 生活服务
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(ChServicesBo bo) {
        ChServices update = MapstructUtils.convert(bo, ChServices.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ChServices entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除生活服务信息
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
    public int updateStatus(ChServicesStatusBo bo) {
        return baseMapper.update(null,
            new LambdaUpdateWrapper<ChServices>()
                .set(ChServices::getStatus, bo.getStatus())
                .eq(ChServices::getServicesId, bo.getServicesId()));
    }

    @Override
    public Boolean publishNews(String status, String name) {
        List<Long> userIds = new ArrayList<>();
        if (status.equals("1")){
            List<String> keys = StpUtil.searchTokenValue("", 0, -1, false);
            for (String key : keys) {
                String token = StringUtils.substringAfterLast(key, ":");
                if (StpUtil.stpLogic.getTokenActiveTimeoutByToken(token) < -1) {
                    continue;
                }
                String idObj = (String) StpUtil.getLoginIdByToken(token);
                Long id = Long.valueOf(StringUtils.substringAfterLast(idObj, ":"));
                String userType = StringUtils.substringBefore(idObj, ":");
                if (!userType.equals("sys_user")) {
                    userIds.add(id);
                }
            }
            scheduledExecutorService.schedule(() -> {
                SseMessageDto dto = new SseMessageDto();
                dto.setMessage(name);
                dto.setUserIds(userIds);
                SseMessageUtils.publishMessage(dto);
            }, 5, TimeUnit.SECONDS);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Category> queryCategory(String type) {
        List<Category> res = new ArrayList<>();
        List<String> categorys = baseMapper.selectClassification();
        if (CollectionUtils.isEmpty(categorys)){
            return res;
        }
        for (String category : categorys) {
            Category c = new Category();
            List<ServicesItemVo> items = new ArrayList<>();
            c.setName(category);
            List<ChServicesVo> vos = baseMapper.selectVoList(new LambdaQueryWrapper<ChServices>().eq(ChServices::getClassification, category));
            vos.forEach(r ->{
                if (r.getType().equals(type)){
                    ServicesItemVo vo = new ServicesItemVo();
                    vo.setServicesId(r.getServicesId());
                    vo.setName(r.getName());
                    vo.setKey(r.getName());
                    vo.setIcon(r.getIcon());
                    vo.setPrice(r.getPrice());
                    items.add(vo);
                }
            });
            c.setServices(items);
            res.add(c);
        }
        return res;
    }

    @Override
    public List<Category> queryAll(String type, String name) {
        List<Category> res = new ArrayList<>();
        List<String> categorys = baseMapper.selectClassification();
        if (CollectionUtils.isEmpty(categorys)){
            return res;
        }
        for (String category : categorys) {
            Category c = new Category();
            List<ServicesItemVo> items = new ArrayList<>();
            c.setName(category);
            List<ChServicesVo> vos = baseMapper.selectVoList(new LambdaQueryWrapper<ChServices>().eq(ChServices::getClassification, category).like(ChServices::getName, name));
            if (vos.isEmpty()) {
                return res;
            }
            vos.forEach(r ->{
                if (r.getType().equals(type)){
                    ServicesItemVo vo = new ServicesItemVo();
                    vo.setServicesId(r.getServicesId());
                    vo.setName(r.getName());
                    vo.setKey(r.getName());
                    vo.setIcon(r.getIcon());
                    vo.setPrice(r.getPrice());
                    items.add(vo);
                }
            });
            c.setServices(items);
            res.add(c);
        }
        return res;
    }
}
