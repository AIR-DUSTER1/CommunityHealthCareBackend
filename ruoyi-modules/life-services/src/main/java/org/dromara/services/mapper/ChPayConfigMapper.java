package org.dromara.services.mapper;

import org.dromara.services.domain.ChPayConfig;
import org.dromara.services.domain.vo.ChPayConfigVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
/**
 * 支付宝配置管理Mapper接口
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
@Mapper
public interface ChPayConfigMapper extends BaseMapperPlus<ChPayConfig, ChPayConfigVo> {

}
