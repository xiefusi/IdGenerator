package com.github.xiefusi.generator.persistence.dao;

import com.github.xiefusi.generator.persistence.properties.KeyGeneratorProperties;
import com.github.xiefusi.generator.persistence.model.GeneratorModel;

import java.util.List;

public interface IDAllocDao {
    /**
     * 初始化业务id记录
     */
    boolean initBusinessRecord(KeyGeneratorProperties.Business business);
    /**
     * 查询所有的id生成记录
     */
    List<GeneratorModel> getAllGeneratorModel();

    /**
     * 查询所有的id生成记录
     */
    GeneratorModel updateMaxIdAndGetGeneratorModel(String tag);

    /**
     * 查询所有的id生成记录
     */
    GeneratorModel updateMaxIdByCustomStepAndGetGeneratorModel(GeneratorModel leafAlloc);

    /**
     * 查询所有的id生成记录
     */
    List<String> getAllBusinessId();

    /**
     * 初始化表
     */
    boolean initTable();
}
