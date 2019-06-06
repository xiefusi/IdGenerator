package io.xiefs.utils.generator.autoconfig;

import io.xiefs.utils.generator.persistence.properties.KeyGeneratorProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 * Author xie
 * Date 2019/6/6 下午3:13
 */
@Configuration
@ComponentScan("io.xiefs.utils.generator.persistence")
@EnableConfigurationProperties(KeyGeneratorProperties.class)
//@EnableAutoConfiguration
public class KeyGeneratorAutoConfig {
}
