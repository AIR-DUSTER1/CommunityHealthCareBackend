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
    // @EncryptField(algorithm=AlgorithmType.SM2, privateKey = "${MYBATIS_ENCRYPTOR_SM2_PRIVATE_KEY}", publicKey = "${MYBATIS_ENCRYPTOR_SM2_PUBLIC_KEY}")
    @EncryptField(algorithm = AlgorithmType.RSA, privateKey = "${MYBATIS_ENCRYPTOR_PRIVATE_KEY}", publicKey = "${MYBATIS_ENCRYPTOR_PUBLIC_KEY}")
    private String testKey;

    /**
     * 值
     */
    // @EncryptField // 什么也不写走默认yml配置
    // @EncryptField(algorithm = AlgorithmType.SM4, password = "${MYBATIS_ENCRYPTOR_PASSWORD}")
    @EncryptField(algorithm = AlgorithmType.AES, password = "${MYBATIS_ENCRYPTOR_PASSWORD}")
    private String value;

}
