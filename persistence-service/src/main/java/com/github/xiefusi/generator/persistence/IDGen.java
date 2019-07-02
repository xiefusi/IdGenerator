package com.github.xiefusi.generator.persistence;

import com.github.xiefusi.generator.persistence.model.Result;

public interface IDGen {
    Result get(String key);

    boolean init();
}
