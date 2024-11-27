package org.dromara.news.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.news.domain.ChNews;
import org.dromara.news.domain.vo.ChNewsVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 新闻信息Mapper接口
 *
 * @author AIR-DUSTER1
 * @date 2024-11-27
 */
@Mapper
public interface ChNewsMapper extends BaseMapperPlus<ChNews, ChNewsVo> {

}
