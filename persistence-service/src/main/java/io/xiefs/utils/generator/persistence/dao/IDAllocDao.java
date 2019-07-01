package io.xiefs.utils.generator.persistence.dao;

import io.xiefs.utils.generator.persistence.model.GeneratorModel;
import io.xiefs.utils.generator.persistence.properties.KeyGeneratorProperties;

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
