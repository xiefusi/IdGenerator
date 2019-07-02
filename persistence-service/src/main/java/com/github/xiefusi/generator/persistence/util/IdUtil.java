package com.github.xiefusi.generator.persistence.util;

import com.github.xiefusi.generator.persistence.model.Result;
import com.github.xiefusi.generator.persistence.service.KeyGeneratorService;

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
