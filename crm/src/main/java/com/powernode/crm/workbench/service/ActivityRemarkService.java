package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {
    /**
     * 根据市场活动主键查询市场备注详情
     * @param activityId
     * @return
     */
    List<ActivityRemark> queryActivityDetailByActivityId(String activityId);

    /**
     * 保存市场活动备注
     * @param activityRemark
     * @return
     */
    int saveActivityRemark(ActivityRemark activityRemark);

    /**
     * 删除市场活动备注
     * @param id
     * @return
     */
    int deleteActivityRemarkById(String id);

    /**
     * 保存修改的市场活动备注
     * @param activityRemark
     * @return
     */
    int saveEditActivityRemark(ActivityRemark activityRemark);
}
