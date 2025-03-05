package org.dromara.news.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import org.dromara.system.domain.SysUser;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.system.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.dromara.news.domain.bo.ChNewsBo;
import org.dromara.news.domain.vo.ChNewsVo;
import org.dromara.news.domain.ChNews;
import org.dromara.news.mapper.ChNewsMapper;
import org.dromara.news.service.IChNewsService;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 新闻信息Service业务层处理
 *
 * @author AIR-DUSTER1
 * @date 2024-11-27
 */
@RequiredArgsConstructor
@Service
public class ChNewsServiceImpl implements IChNewsService {

    private final ChNewsMapper baseMapper;
    private final ScheduledExecutorService scheduledExecutorService;

    /**
     * 查询新闻信息
     *
     * @param newsId 主键
     * @return 新闻信息
     */
    @Override
    public ChNewsVo queryById(Long newsId){
        ChNewsVo chNewsVo = baseMapper.selectVoById(newsId);
        if (chNewsVo != null) {
            ChNews chNews = MapstructUtils.convert(chNewsVo, ChNews.class);
            if (chNews != null) {
                chNews.setViews(chNewsVo.getViews() + 1);
                baseMapper.updateById(chNews);
            }
        }
        return chNewsVo;
    }

    /**
     * 分页查询新闻信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 新闻信息分页列表
     */
    @Override
    public TableDataInfo<ChNewsVo> queryPageList(ChNewsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ChNews> lqw = buildQueryWrapper(bo);
        Page<ChNewsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的新闻信息列表
     *
     * @param bo 查询条件
     * @return 新闻信息列表
     */
    @Override
    public List<ChNewsVo> queryList(ChNewsBo bo) {
        LambdaQueryWrapper<ChNews> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChNews> buildQueryWrapper(ChNewsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ChNews> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getAuthor()), ChNews::getAuthor, bo.getAuthor());
        lqw.like(StringUtils.isNotBlank(bo.getSource()), ChNews::getSource, bo.getSource());
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), ChNews::getTitle, bo.getTitle());
        lqw.eq(bo.getStatus() != null, ChNews::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增新闻信息
     *
     * @param bo 新闻信息
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(ChNewsBo bo) {
        ChNews add = MapstructUtils.convert(bo, ChNews.class);
        String slug = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        if (add != null) {
            add.setSlug(slug);
        }
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setNewsId(add.getNewsId());
        }
        return flag;
    }

    /**
     * 修改新闻信息
     *
     * @param bo 新闻信息
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(ChNewsBo bo) {
        ChNews update = MapstructUtils.convert(bo, ChNews.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ChNews entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除新闻信息信息
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

    /**
     * 更新新闻状态
     * @param newsId
     * @param status
     * @return
     */
    @Override
    public int updateNewsStatus(Long newsId, String status) {
        return baseMapper.update(null,
            new LambdaUpdateWrapper<ChNews>()
                .set(ChNews::getStatus, status)
                .eq(ChNews::getNewsId, newsId));
    }

    @Override
    public Boolean publishNews(String status,String msg) {
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
                dto.setMessage(msg);
                dto.setUserIds(userIds);
                SseMessageUtils.publishMessage(dto);
            }, 5, TimeUnit.SECONDS);
            return true;
        }else{
            return false;
        }
    }
}
