package org.dromara.services.mapper;

import org.dromara.services.domain.ChServicesAccount;
import org.dromara.services.domain.vo.ChServicesAccountVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
/**
 * 账户管理Mapper接口
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
@Mapper
public interface ChServicesAccountMapper extends BaseMapperPlus<ChServicesAccount, ChServicesAccountVo> {
    String selectUserByUserId(Long userId);
    int selectAccountByUserId(Long userId);
    Long selectAccountIdByUserId(Long userId);
}
