package com.github.xiefusi.generator.autoconfig;

import com.github.xiefusi.generator.persistence.config.PersistenceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Description:
 * Author xie
 * Date 2019/6/6 下午3:13
 */
@Configuration
@Import(PersistenceConfig.class)
public class KeyGeneratorAutoConfig {
}
