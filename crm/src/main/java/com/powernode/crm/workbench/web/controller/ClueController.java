package com.powernode.crm.workbench.web.controller;

import com.powernode.crm.commons.constants.Constant;
import com.powernode.crm.commons.domain.ReturnObject;
import com.powernode.crm.commons.utils.DateUtils;
import com.powernode.crm.commons.utils.UUIDUtils;
import com.powernode.crm.settings.domain.DicValue;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.settings.service.DicValueService;
import com.powernode.crm.settings.service.UserService;
import com.powernode.crm.workbench.domain.Activity;
import com.powernode.crm.workbench.domain.Clue;
import com.powernode.crm.workbench.domain.ClueActivityRelation;
import com.powernode.crm.workbench.domain.ClueRemark;
import com.powernode.crm.workbench.mapper.ActivityMapper;
import com.powernode.crm.workbench.service.ActivityService;
import com.powernode.crm.workbench.service.ClueActivityRelationService;
import com.powernode.crm.workbench.service.ClueRemarkService;
import com.powernode.crm.workbench.service.ClueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.PublicKey;
import java.util.*;

@Controller
public class ClueController {
    @Autowired
    private UserService userService;
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private ClueService clueService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ClueRemarkService clueRemarkService;
    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/workbench/clue/index.do")
    public String index(HttpServletRequest request){
        //查询所有的用户信息
        List<User> userList = userService.queryAllUsers();
        //查询字典数据
        List<DicValue> appellationList = dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList = dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        //把数据保存到request中
        request.setAttribute("userList", userList);
        request.setAttribute("appellationList", appellationList);
        request.setAttribute("clueStateList", clueStateList);
        request.setAttribute("sourceList", sourceList);
        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/saveClue.do")
    public @ResponseBody Object saveClue(Clue clue, HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();
        //处理参数
        //封装
        clue.setCreateTime(DateUtils.forMateDateTime(new Date()));
        clue.setCreateBy(user.getId());
        clue.setId(UUIDUtils.getUUID());
        try {
            //调用service层保存线索对象
            int record = clueService.saveClue(clue);
            if(record > 0){
                //保存成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后....");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/queryCountOfClueByConditionForPage.do")
    public @ResponseBody Object queryCountOfClueByConditionForPage(String fullname, String company,String phone, String source,
                                                            String owner, String mphone, String state, int pageSize,int pageNo){
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("fullname", fullname);
        map.put("company", company);
        map.put("phone", phone);
        map.put("source", source);
        map.put("owner", owner);
        map.put("mphone", mphone);
        map.put("state", state);
        map.put("beginNo", pageSize * (pageNo - 1));
        map.put("pageSize", pageSize);

        //查询数据
        List<Clue> clueList = clueService.queryClueByConditionForPage(map);
        int totalRows = clueService.queryCountOfClueByCondition(map);
        //封装数据
        Map<String, Object> ret = new HashMap<>();
        ret.put("clueList", clueList);
        ret.put("totalRows", totalRows);
        return ret;

    }

    @RequestMapping("/workbench/clue/DetailClue.do")
    public String DetailClue(String id, HttpServletRequest request){
        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);
        Clue clue = clueService.queryClueForDetailById(id);
        List<ClueRemark> clueRemarkList = clueRemarkService.queryClueRemarkForDetailByClueId(id);
        request.setAttribute("activityList", activityList);
        request.setAttribute("clue", clue);
        request.setAttribute("clueRemarkList", clueRemarkList);
        return "workbench/clue/detail";
    }
    @RequestMapping("/workbench/clue/queryActivityForDetailByNameClueId.do")
    public @ResponseBody Object queryActivityForDetailByNameClueId(String name, String clueId){
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("clueId", clueId);
        List<Activity> activityList = activityService.queryActivityForDetailByNameClueId(map);
        return activityList;
    }

    @RequestMapping("/workbench/clue/saveBund.do")
    public @ResponseBody Object saveBund(String[] ids, String clueId){
        List<ClueActivityRelation> list = new ArrayList<>();
        ClueActivityRelation relation = null;
        ReturnObject returnObject = null;
        //循环遍历id
            for(String id: ids){
                //封装实体类对象
                relation = new ClueActivityRelation();
                relation.setId(UUIDUtils.getUUID());
                relation.setClueId(clueId);
                relation.setActivityId(id);
                list.add(relation);
            }
        try {
            returnObject = new ReturnObject();
            //调用service层保存数据
            int record = clueActivityRelationService.saveCreateClueActivityRelationByList(list);
            if(record > 0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                List<Activity> activityList = activityService.queryActivityByIds(ids);
                returnObject.setRetData(activityList);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后....");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/deleteBund.do")
    public @ResponseBody Object deleteBund(String clueId, String activityId){
        ReturnObject returnObject = new ReturnObject();
        //封装参数
        ClueActivityRelation relation = new ClueActivityRelation();
        relation.setActivityId(activityId);
        relation.setClueId(clueId);
        try {
            //调用service层删除数据
            int record = clueActivityRelationService.deleteClueActivityRelationById(relation);
            if(record > 0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后....");
        }
        return returnObject;
    }
    @RequestMapping("/workbench/clue/toConvert.do")
    public String toConvert(String id, HttpServletRequest request){
        //查询线索明细
        Clue clue = clueService.queryClueForDetailById(id);
        //查询阶段的数据字典
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        //放到作用域
        request.setAttribute("clue", clue);
        request.setAttribute("stageList", stageList);
        //转发
        return "workbench/clue/convert";
    }

    @RequestMapping("/workbench/clue/queryActivityForConvertByNameClueId.do")
    public @ResponseBody Object queryActivityForConvertByNameClueId(String activityName, String clueId){
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("name", activityName);
        map.put("clueId", clueId);
        //调用service层查询
        List<Activity> activityList = activityService.queryActivityForConvertByNameClueId(map);
        return activityList;
    }

    @RequestMapping("/workbench/clue/saveConvertClue.do")
    public @ResponseBody Object saveConvertClue(String clueId,String money,String name,String expectedDate,String stage, String activityId,String isCreateTran, HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("clueId", clueId);
        map.put("money", money);
        map.put("name", name);
        map.put("expectedDate", expectedDate);
        map.put("stage", stage);
        map.put("activityId", activityId);
        map.put("isCreateTran", isCreateTran);
        map.put(Constant.SESSION_USER, user);
        try {
            //调用service
            clueService.saveConvertClue(map);
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后....");
        }
        return returnObject;
    }

}
