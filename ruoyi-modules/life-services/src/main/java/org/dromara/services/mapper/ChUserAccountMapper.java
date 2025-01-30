package org.dromara.services.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.services.domain.ChUserAccount;

import java.util.List;

/**
 * 用户与账户关联表 数据层
 *
 * @author AIR-DUSTER1
 * @date 2024-12-31
 */
@Mapper
public interface ChUserAccountMapper extends BaseMapperPlus<ChUserAccount, ChUserAccount> {
    List<Long> selectUserIdsByAccountId(Long AccountId);

}
