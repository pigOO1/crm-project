package com.powernode.crm.settings.service.impl;

import com.powernode.crm.settings.domain.DicValue;
import com.powernode.crm.settings.mapper.DicValueMapper;
import com.powernode.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dicValueService")
public class DicValueServiceImpl implements DicValueService {

    @Autowired
    private DicValueMapper dicValueMapper;
    /**
     * 根据字典类型查询字典数据列表
     * @param typeCode
     * @return
     */
     @Override
     public List<DicValue> queryDicValueByTypeCode(String typeCode) {
         return dicValueMapper.selectDicValueByTypeCode(typeCode);
     }
}
