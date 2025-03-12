package org.dromara.services.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.pay.config.AliPayConfig;
import org.dromara.services.domain.ChPayConfig;
import org.dromara.services.mapper.ChPayConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitAliPay {

    private final ChPayConfigMapper baseMapper;

    @Autowired
    public InitAliPay(ChPayConfigMapper baseMapper) {
        this.baseMapper = baseMapper;
    }

    @PostConstruct
    public void init() {
        LambdaQueryWrapper<ChPayConfig> lqw = Wrappers.lambdaQuery();
        lqw.eq(ChPayConfig::getStatus, "1");
        ChPayConfig chPayConfig = baseMapper.selectOne(lqw);
        if (chPayConfig != null) {
        AliPayConfig aliPayConfig = new AliPayConfig();
        aliPayConfig.setAppId(chPayConfig.getAppId());
        aliPayConfig.setAppPrivateKey(chPayConfig.getPrivateKey());
        aliPayConfig.setAlipayPublicKey(chPayConfig.getPublicKey());
        aliPayConfig.setNotifyUrl(chPayConfig.getNotifyUrl());
        aliPayConfig.setReturnUrl(chPayConfig.getReturnUrl());
        log.info("初始化支付宝成功");
        } else {
            log.error("未找到有效的支付配置");
        }
    }
}
