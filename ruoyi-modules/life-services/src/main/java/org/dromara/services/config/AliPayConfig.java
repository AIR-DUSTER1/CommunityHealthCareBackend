package org.dromara.services.config;

import com.alipay.api.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里支付配置类
 *
 * @author xiaokai54
 * @date 2024/9/3
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
	/**
	 * 应用APPID
	 */
	private String appId;
	/**
	 * 应用私钥
	 */
	private String appPrivateKey;
	/**
	 * 支付宝公钥
	 */
	private String alipayPublicKey;
	/**
	 * 支付宝回调地址
	 */
	private String notifyUrl;
	/**
	 * 支付宝同步回调地址
	 */
	private String returnUrl;

	@Bean
	public AlipayClient alipayClient() throws AlipayApiException {
		AlipayConfig alipayConfig = new AlipayConfig();
		// 设置appId
		alipayConfig.setAppId(appId);
		// 设置APP私钥
		alipayConfig.setPrivateKey(appPrivateKey);
		// 设置支付宝公钥
		alipayConfig.setAlipayPublicKey(alipayPublicKey);
		// 设置支付宝网关地址
		alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
		// 设置请求格式,固定值json.
		alipayConfig.setFormat(AlipayConstants.FORMAT_JSON);
		// 设置字符集
		alipayConfig.setCharset(AlipayConstants.CHARSET_UTF8);
		// 设置签名类型
		alipayConfig.setSignType(AlipayConstants.SIGN_TYPE_RSA2);
		// 构造client
		return new DefaultAlipayClient(alipayConfig);
	}

}
