package com.powernode.crm.settings.service;

import com.powernode.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueService {

    /**
     * 根据字典类型查询字典数据列表
     * @param typeCode
     * @return
     */
    List<DicValue> queryDicValueByTypeCode(String typeCode);
}
