package org.dromara.web.service.impl;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.constant.Constants;
import org.dromara.common.core.constant.GlobalConstants;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.domain.model.SmsLoginBody;
import org.dromara.common.core.enums.LoginType;
import org.dromara.common.core.enums.UserStatus;
import org.dromara.common.core.enums.UserType;
import org.dromara.common.core.exception.user.CaptchaExpireException;
import org.dromara.common.core.exception.user.UserException;
import org.dromara.common.core.utils.MessageUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.core.utils.ValidatorUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.domain.SysUser;
import org.dromara.system.domain.vo.SysClientVo;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.system.mapper.SysUserMapper;
import org.dromara.web.domain.vo.LoginVo;
import org.dromara.web.service.IAuthStrategy;
import org.dromara.web.service.SysLoginService;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 短信认证策略
 *
 * @author Michelle.Chung
 */
@Slf4j
@Service("sms" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class SmsAuthStrategy implements IAuthStrategy {

    private final SysLoginService loginService;
    private final SysUserMapper userMapper;

    @Override
    public LoginVo login(String body, SysClientVo client) {
        SmsLoginBody loginBody = JsonUtils.parseObject(body, SmsLoginBody.class);
        ValidatorUtils.validate(loginBody);
        String phoneNumber = loginBody.getPhoneNumber();
        String smsCode = loginBody.getSmsCode();
        SysUserVo user = loadUserByPhonenumber(phoneNumber);

        loginService.checkLogin(LoginType.SMS, user.getUserName(), () -> !validateSmsCode(phoneNumber, smsCode));

        LoginUser loginUser = loginService.buildLoginUser(user);
        loginUser.setClientKey(client.getClientKey());
        loginUser.setDeviceType(client.getDeviceType());
        SaLoginModel model = new SaLoginModel();
        model.setDevice(client.getDeviceType());
        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
        // 例如: 后台用户30分钟过期 app用户1天过期
        model.setTimeout(client.getTimeout());
        model.setActiveTimeout(client.getActiveTimeout());
        model.setExtra(LoginHelper.CLIENT_KEY, client.getClientId());
        // 生成token
        LoginHelper.login(loginUser, model);

        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setClientId(client.getClientId());
        return loginVo;
    }

    /**
     * 校验短信验证码
     */
    private boolean validateSmsCode(String phoneNumber, String smsCode) {
        String code = RedisUtils.getCacheObject(GlobalConstants.CAPTCHA_CODE_KEY + phoneNumber);
        if (StringUtils.isBlank(code)) {
            loginService.recordLogininfor( phoneNumber, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        return code.equals(smsCode);
    }

    private SysUserVo loadUserByPhonenumber(String phoneNumber) {
        SysUserVo user = userMapper.selectVoOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhoneNumber, phoneNumber));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", phoneNumber);
            return autoRegisterUser(phoneNumber);
//            throw new UserException("user.not.exists", phoneNumber);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", phoneNumber);
            throw new UserException("user.blocked", phoneNumber);
        }
        return user;
    }
    private SysUserVo autoRegisterUser(String phoneNumber) {
        SysUser newUser = new SysUser();
        newUser.setPhoneNumber(phoneNumber);
        newUser.setNickName(phoneNumber);
        newUser.setUserType(UserType.APP_USER.getUserType());
        newUser.setUserName(phoneNumber); // 可以根据需求设置用户名
        newUser.setPassword(generateRandomPassword(10)); // 生成随机密码
        newUser.setStatus(String.valueOf(UserStatus.OK.getCode())); // 设置用户状态为正常
        userMapper.insert(newUser);

        // 获取新注册用户的详细信息
        return userMapper.selectVoOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhoneNumber, phoneNumber));
    }
    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ${SA_TOKEN_JWT_SECRET}0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
