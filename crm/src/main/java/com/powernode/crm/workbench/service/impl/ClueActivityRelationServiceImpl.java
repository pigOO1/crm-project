package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.workbench.domain.ClueActivityRelation;
import com.powernode.crm.workbench.mapper.ClueActivityRelationMapper;
import com.powernode.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("clueActivityRelationService")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Override
    public int saveCreateClueActivityRelationByList(List<ClueActivityRelation> list) {
        return clueActivityRelationMapper.insertClueActivityRelationByList(list);
    }

    @Override
    public int deleteClueActivityRelationById(ClueActivityRelation relation) {
        return clueActivityRelationMapper.deleteClueActivityRelationById(relation);
    }
}
