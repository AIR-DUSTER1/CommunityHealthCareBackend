package org.dromara.services.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.services.domain.ChUserAccount;
import org.dromara.services.mapper.ChUserAccountMapper;
import org.springframework.stereotype.Service;
import org.dromara.services.domain.bo.ChServicesAccountBo;
import org.dromara.services.domain.vo.ChServicesAccountVo;
import org.dromara.services.domain.ChServicesAccount;
import org.dromara.services.mapper.ChServicesAccountMapper;
import org.dromara.services.service.IChServicesAccountService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 账户管理Service业务层处理
 *
 * @author AIR-DUSTER1
 * @date 2024-12-29
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ChServicesAccountServiceImpl implements IChServicesAccountService {

    private final ChServicesAccountMapper baseMapper;
    private final ChUserAccountMapper chUserAccountMapper;
    /**
     * 查询账户管理
     *
     * @param accountId 主键
     * @return 账户管理
     */
    @Override
    public ChServicesAccountVo queryById(Long accountId){
        return baseMapper.selectVoById(accountId);
    }

    /**
     * 分页查询账户管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 账户管理分页列表
     */
    @Override
    public TableDataInfo<ChServicesAccountVo> queryPageList(ChServicesAccountBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ChServicesAccount> lqw = buildQueryWrapper(bo);
        Page<ChServicesAccountVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的账户管理列表
     *
     * @param bo 查询条件
     * @return 账户管理列表
     */
    @Override
    public List<ChServicesAccountVo> queryList(ChServicesAccountBo bo) {
        LambdaQueryWrapper<ChServicesAccount> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChServicesAccount> buildQueryWrapper(ChServicesAccountBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ChServicesAccount> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getAccountName()), ChServicesAccount::getAccountName, bo.getAccountName());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), ChServicesAccount::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getPhysicalCondition()), ChServicesAccount::getPhysicalCondition, bo.getPhysicalCondition());
        lqw.eq(StringUtils.isNotBlank(bo.getRealNameStatus()), ChServicesAccount::getRealNameStatus, bo.getRealNameStatus());
        return lqw;
    }

    /**
     * 新增账户管理
     *
     * @param bo 账户管理
     * @return 是否新增成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(ChServicesAccountBo bo) {
        String userName = baseMapper.selectUserByUserId(bo.getUserId());
        if (StringUtils.isBlank(userName)){
            throw new RuntimeException("用户不存在");
        }
        bo.setAccountName(userName);
        ChServicesAccount add = MapstructUtils.convert(bo, ChServicesAccount.class);
        if (add ==null) {
            return false;
        }
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            ChUserAccount chUserAccount = new ChUserAccount();
            chUserAccount.setAccountId(add.getAccountId());
            chUserAccount.setUserId(add.getUserId());
            int i = chUserAccountMapper.insert(chUserAccount);
            if (i <= 0) {
                throw new RuntimeException("添加用户账户失败");
            }
        }
        return flag;
    }

    /**
     * 修改账户管理
     *
     * @param bo 账户管理
     * @return 是否修改成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(ChServicesAccountBo bo) {
        ChServicesAccount update = MapstructUtils.convert(bo, ChServicesAccount.class);
        validEntityBeforeSave(update);
        boolean flag=baseMapper.updateById(update) > 0;
        if (flag) {
            ChUserAccount chUserAccount = chUserAccountMapper.selectOne(new LambdaQueryWrapper<ChUserAccount>().eq(ChUserAccount::getUserId, update.getUserId()));
            if (!Objects.equals(bo.getUserId(), chUserAccount.getUserId())) {
                chUserAccount.setUserId(bo.getUserId());
                int i = chUserAccountMapper.updateById(chUserAccount);
                if (i <= 0) {
                    throw new RuntimeException("修改用户账户失败");
                }
            }

        }
        return flag;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ChServicesAccount entity){
        //TODO 做一些数据校验,如唯一约束
        if (entity.getUserId() == null){
            throw new RuntimeException("userId为空");
        }
        int i = baseMapper.selectAccountByUserId(entity.getUserId());
        if (i > 0){
            throw new RuntimeException("已存在账户");
        }
    }

    /**
     * 校验并批量删除账户管理信息
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
        LambdaQueryWrapper<ChServicesAccount> lqw = Wrappers.lambdaQuery();
        lqw.eq(ChServicesAccount::getUserId, ids);

        return baseMapper.update(null, new LambdaUpdateWrapper<ChServicesAccount>()
            .set(ChServicesAccount::getStatus, "2")) > 0;
    }

    @Override
    public Long getAccountIdByUserId(Long userId) {
        return baseMapper.selectAccountIdByUserId(userId);
    }

    public void updatePrice(Long userId, BigDecimal price,boolean isAdd){
        ChServicesAccount chServicesAccount = baseMapper.selectOne(new LambdaQueryWrapper<ChServicesAccount>().eq(ChServicesAccount::getUserId, userId));
        if (isAdd){
            chServicesAccount.setBalance(chServicesAccount.getBalance().add(price));
            baseMapper.update(chServicesAccount, new LambdaUpdateWrapper<ChServicesAccount>().eq(ChServicesAccount::getAccountId, chServicesAccount.getAccountId()));
        }else{
            chServicesAccount.setFreezeBalance(price);
            baseMapper.update(chServicesAccount, new LambdaUpdateWrapper<ChServicesAccount>().eq(ChServicesAccount::getAccountId, chServicesAccount.getAccountId()));
        }
    }

}
