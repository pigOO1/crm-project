package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    /**
     * 保存市场活动信息
     */
    int savaCreateActivity(Activity activity);

    /**
     * 根据条件分页查询
     * @param map
     * @return
     */
    List<Activity> queryActivityByConditionForPage(Map<String, Object> map);

    /**
     * 查询所有符合条件的记录条数
     * @param map
     * @return
     */
    int queryCountOfActivityByCondition(Map<String, Object> map);

    /**
     * 根据id数组批量删除activity
     * @param ids
     * @return
     */
    int deleteActivityByIds(String[] ids);

    /**
     * 根据id查询市场活动信息
     * @param id
     * @return
     */
    Activity queryActivityById(String id);

    /**
     * 保存修改的市场活动
     * @param activity
     * @return
     */
    int saveEditActivity(Activity activity);

    /**
     * 查询所有的市场活动
     * @return
     */
    List<Activity> queryAllActivities();

    /**
     * 根据ids查询市场活动
     * @param ids
     * @return
     */
    List<Activity> querySelectActivities(String[] ids);

    /**
     *批量保存市场活动
     * @param activities
     * @return
     */
    int saveCreateActivitiesByList(List<Activity> activities);

    /**
     * 查询市场明细
     * @param id
     * @return
     */
    Activity queryActivityForDetailById(String id);

    /**
     * 根据线索id查询市场活动详情
     * @param clueId
     * @return
     */
    List<Activity> queryActivityForDetailByClueId(String clueId);

    /**
     * 根据市场活动的name模糊查询市场活动，并且排除掉已经关联过的clue
     * @param map
     * @return
     */
    List<Activity> queryActivityForDetailByNameClueId(Map<String, Object> map);

    List<Activity> queryActivityByIds(String[] ids);

    List<Activity> queryActivityForConvertByNameClueId(Map<String, Object> map);

    List<Activity> queryActivityByName(String name);
}
