package io.xiefs.utils.generator.persistence.util;

import io.xiefs.utils.generator.persistence.service.KeyGeneratorService;

import java.util.Objects;

/**
 * Description:
 * Author xie
 * Date 2019/6/6 下午4:25
 */
public class IdUtil {
    private static KeyGeneratorService service;

    public static void init(KeyGeneratorService service) {
        if (Objects.isNull(service)) {
            IdUtil.service = service;
        }
    }

    public static synchronized Long getId() {
        return null;
    }
}
