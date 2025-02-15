package org.dromara.common.unipush.config;

import org.dromara.common.unipush.handler.UniPushHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class UniPushConfig {
    @Bean
    public UniPushHandler uniPushHandler() {
        return new UniPushHandler();
    }
}
