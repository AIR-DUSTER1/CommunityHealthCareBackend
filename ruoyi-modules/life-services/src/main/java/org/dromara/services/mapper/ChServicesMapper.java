package org.dromara.services.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.services.domain.ChServices;
import org.dromara.services.domain.vo.ChServicesVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 生活服务Mapper接口
 *
 * @author AIR-DUSTER1
 * @date 2024-12-13
 */
@Mapper
public interface ChServicesMapper extends BaseMapperPlus<ChServices, ChServicesVo> {

}
