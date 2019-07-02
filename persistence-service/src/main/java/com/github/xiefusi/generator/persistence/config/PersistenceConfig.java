package com.github.xiefusi.generator.persistence.config;

import com.github.xiefusi.generator.persistence.properties.KeyGeneratorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 * Author xie
 * Date 2019/7/1 上午10:51
 */
@Configuration
@EnableConfigurationProperties(KeyGeneratorProperties.class)
@ComponentScan({
        "io.xiefs.utils.generator.persistence.dao",
        "io.xiefs.utils.generator.persistence.service"
})
public class PersistenceConfig {
}
