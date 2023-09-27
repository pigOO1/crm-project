package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.FunnelVo;
import com.powernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranService {
    void saveCreateTran(Map<String, Object> map);

    Tran queryTranForDetailById(String id);

    List<FunnelVo> queryCountOfTranGroupByStage();
}
