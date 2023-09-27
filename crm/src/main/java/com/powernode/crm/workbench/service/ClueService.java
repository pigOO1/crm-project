package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {
    /**
     * 保存线索
     * @param clue
     * @return
     */
    int saveClue(Clue clue);

    /**
     * 分页查询线索
     * @return
     */
    List<Clue> queryClueByConditionForPage(Map<String, Object> map);

    /**
     * 查询线索总数
     * @param map
     * @return
     */
    int queryCountOfClueByCondition(Map<String, Object> map);

    /**
     * 查询线索明细
     * @param id
     * @return
     */
    Clue queryClueForDetailById(String id);

    void saveConvertClue(Map<String, Object> map);
}
