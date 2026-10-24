package org.dromara.demo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import org.dromara.common.encrypt.annotation.EncryptField;
import org.dromara.common.encrypt.enumd.AlgorithmType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("test_demo")
public class TestDemoEncrypt extends TestDemo {

    /**
     * key键
     */
    // 密钥请在 mybatis-encryptor 配置中通过环境变量提供，禁止硬编码
    @EncryptField(algorithm = AlgorithmType.RSA)
    private String testKey;

    /**
     * 值
     */
    // @EncryptField // 什么也不写走默认yml配置
    @EncryptField(algorithm = AlgorithmType.AES)
    private String value;

}
