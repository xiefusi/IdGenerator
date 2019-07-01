package io.xiefs.utils.generator.persistence;

import io.xiefs.utils.generator.persistence.model.Result;

public interface IDGen {
    Result get(String key);

    boolean init();
}
