package org.dromara.news.service;

import org.dromara.news.domain.vo.ChNewsVo;
import org.dromara.news.domain.bo.ChNewsBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 新闻信息Service接口
 *
 * @author AIR-DUSTER1
 * @date 2024-11-27
 */
public interface IChNewsService {

    /**
     * 查询新闻信息
     *
     * @param newsId 主键
     * @return 新闻信息
     */
    ChNewsVo queryById(Long newsId);

    /**
     * 分页查询新闻信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 新闻信息分页列表
     */
    TableDataInfo<ChNewsVo> queryPageList(ChNewsBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的新闻信息列表
     *
     * @param bo 查询条件
     * @return 新闻信息列表
     */
    List<ChNewsVo> queryList(ChNewsBo bo);

    /**
     * 新增新闻信息
     *
     * @param bo 新闻信息
     * @return 是否新增成功
     */
    Boolean insertByBo(ChNewsBo bo);

    /**
     * 修改新闻信息
     *
     * @param bo 新闻信息
     * @return 是否修改成功
     */
    Boolean updateByBo(ChNewsBo bo);

    /**
     * 校验并批量删除新闻信息信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 更新新闻状态
     * @param newsId
     * @param status
     * @return
     */
    int updateNewsStatus(Long newsId, String status);

    Boolean publishNews(String status,String msg);
    ChNewsVo getNews(String slug);
}
