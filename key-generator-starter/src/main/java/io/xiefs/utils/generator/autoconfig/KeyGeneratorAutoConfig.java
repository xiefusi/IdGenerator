package io.xiefs.utils.generator.autoconfig;

import io.xiefs.utils.generator.persistence.config.PersistenceConfig;
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
