package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.workbench.domain.Activity;
import com.powernode.crm.workbench.mapper.ActivityMapper;
import com.powernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    /**
     * 保存市场活动信息
     */
    @Override
    public int savaCreateActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }

    /**
     * 根据条件分页查询
     * @param map
     * @return
     */
    @Override
    public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    /**
     * 查询所有符合条件的记录条数
     * @param map
     * @return
     */
    @Override
    public int queryCountOfActivityByCondition(Map<String, Object> map) {
        return activityMapper.selectCountOfActivityByCondition(map);
    }

    /**
     * 根据id数组批量删除activity
     * @param ids
     * @return
     */
    @Override
    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    /**
     * 根据id查询市场活动信息
     * @param id
     * @return
     */
    @Override
    public Activity queryActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    /**
     * 保存修改的市场活动
     * @param activity
     * @return
     */
    @Override
    public int saveEditActivity(Activity activity) {
        return activityMapper.updateActivity(activity);
    }

    /**
     * 查询所有的市场活动
     * @return
     */
    @Override
    public List<Activity> queryAllActivities() {
        return activityMapper.selectAllActivities();
    }

    /**
     * 根据ids查询市场活动
     * @param ids
     * @return
     */
    @Override
    public List<Activity> querySelectActivities(String[] ids) {
        return activityMapper.selectXzActivities(ids);
    }

    /**
     *批量保存市场活动
     * @param activities
     * @return
     */
    @Override
    public int saveCreateActivitiesByList(List<Activity> activities) {
        return activityMapper.insertActivitiesByList(activities);
    }

    /**
     * 查询市场明细
     * @param id
     * @return
     */
    @Override
    public Activity queryActivityForDetailById(String id) {
        return activityMapper.selectActivityForDetailById(id);
    }

    /**
     * 根据线索id查询市场活动详情
     * @param clueId
     * @return
     */
    @Override
    public List<Activity> queryActivityForDetailByClueId(String clueId) {
        return activityMapper.selectActivityForDetailByClueId(clueId);
    }

    /**
     * 根据市场活动的name模糊查询市场活动，并且排除掉已经关联过的clue
     * @param map
     * @return
     */
    @Override
    public List<Activity> queryActivityForDetailByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForDetailByNameClueId(map);
    }

    @Override
    public List<Activity> queryActivityByIds(String[] ids) {
        return activityMapper.selectActivityByIds(ids);
    }

    @Override
    public List<Activity> queryActivityForConvertByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForConvertByNameClueId(map);
    }

    @Override
    public List<Activity> queryActivityByName(String name) {
        return activityMapper.selectActivityByName(name);
    }
}
