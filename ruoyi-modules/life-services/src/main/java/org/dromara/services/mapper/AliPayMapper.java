package org.dromara.services.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.dromara.services.domain.ChServices;

import java.util.List;

/**
 * 支付宝Mapper接口
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
@Mapper
public interface AliPayMapper{
    ChServices userNameByUserId(Long serviceId);
}
