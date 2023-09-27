package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkService {
    /**
     * 根据线索id查询线索备注明细
     * @param clueId
     * @return
     */
    List<ClueRemark> queryClueRemarkForDetailByClueId(String clueId);

    /**
     * 插入线索备注
     * @param clueRemark
     * @return
     */
    int saveClueRemark(ClueRemark clueRemark);

    /**
     * 删除线索备注
     * @param id
     * @return
     */
    int deleteClueRemarkById(String id);

    /**
     * 更新线索备注
     * @param clueRemark
     * @return
     */
    int saveEditClueRemark(ClueRemark clueRemark);
}
