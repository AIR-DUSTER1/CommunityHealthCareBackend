package org.dromara.news.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.system.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.dromara.news.domain.bo.ChNewsBo;
import org.dromara.news.domain.vo.ChNewsVo;
import org.dromara.news.domain.ChNews;
import org.dromara.news.mapper.ChNewsMapper;
import org.dromara.news.service.IChNewsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

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
    @Autowired
    private final SysUserServiceImpl userService;
    /**
     * 查询新闻信息
     *
     * @param newsId 主键
     * @return 新闻信息
     */
    @Override
    public ChNewsVo queryById(Long newsId){
        return baseMapper.selectVoById(newsId);
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
}
