package io.xiefs.utils.generator.persistence.util;

import io.xiefs.utils.generator.persistence.model.Result;
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
        if (Objects.isNull(IdUtil.service)) {
            IdUtil.service = service;
        }
    }

    public static Result get(String key) {
        return service.get(key);
    }

}
