package org.dromara.services.mapper;

import org.dromara.services.domain.ChServicesOrder;
import org.dromara.services.domain.vo.ChServicesOrderVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
/**
 * 服务订单Mapper接口
 *
 * @author AIR-DUSTER1
 * @date 2024-12-24
 */
@Mapper
public interface ChServicesOrderMapper extends BaseMapperPlus<ChServicesOrder, ChServicesOrderVo> {

}
