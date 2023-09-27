package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.workbench.domain.ActivityRemark;
import com.powernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.powernode.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("activityRemarkService")
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    ActivityRemarkMapper activityRemarkMapper;
    /**
     * 根据市场活动主键查询市场备注详情
     * @param activityId
     * @return
     */
    @Override
    public List<ActivityRemark> queryActivityDetailByActivityId(String activityId) {
        return activityRemarkMapper.selectActivityRemarkDetailByActivityId(activityId);
    }

    /**
     * 保存市场活动备注
     * @param activityRemark
     * @return
     */
    @Override
    public int saveActivityRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.insertActivityRemark(activityRemark);
    }

    /**
     * 删除市场活动备注
     * @param id
     * @return
     */
    @Override
    public int deleteActivityRemarkById(String id) {
        return activityRemarkMapper.deleteActivityRemarkById(id);
    }

    /**
     * 保存修改的市场活动备注
     * @param activityRemark
     * @return
     */
    @Override
    public int saveEditActivityRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.updateActivityRemark(activityRemark);
    }
}
