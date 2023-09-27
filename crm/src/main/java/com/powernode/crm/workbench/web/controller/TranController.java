package com.powernode.crm.workbench.web.controller;

import com.powernode.crm.commons.constants.Constant;
import com.powernode.crm.commons.domain.ReturnObject;
import com.powernode.crm.settings.domain.DicValue;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.settings.service.DicValueService;
import com.powernode.crm.settings.service.UserService;
import com.powernode.crm.workbench.domain.*;
import com.powernode.crm.workbench.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class TranController {
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ContactsService contactsService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TranService tranService;
    @Autowired
    private TranRemarkService tranRemarkService;
    @Autowired
    private TranHistoryService tranHistoryService;

    @RequestMapping("/workbench/transaction/index.do")
    public String index(HttpServletRequest request){
        //查询字典
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        request.setAttribute("transactionTypeList", transactionTypeList);
        request.setAttribute("sourceList", sourceList);
        request.setAttribute("stageList", stageList);
        return "workbench/transaction/index";
    }
    @RequestMapping("/workbench/transaction/toSave.do")
    public String toSave(HttpServletRequest request){
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        List<User> userList = userService.queryAllUsers();
        request.setAttribute("transactionTypeList", transactionTypeList);
        request.setAttribute("sourceList", sourceList);
        request.setAttribute("stageList", stageList);
        request.setAttribute("userList", userList);
        return "workbench/transaction/save";
    }
    @RequestMapping("/workbench/transaction/searchActivity.do")
    public @ResponseBody Object searchActivity(String name){
        //调用service查询市场活动
        List<Activity> activityList = activityService.queryActivityByName(name);
        return activityList;
    }
    @RequestMapping("/workbench/transaction/searchContacts.do")
    public @ResponseBody Object searchContacts(String name){
        //调用service查询市场活动
        List<Contacts> contactsList = contactsService.queryContactsByName(name);
        return contactsList;
    }
    @RequestMapping("/workbench/transaction/getPossibilityByStage.do")
    public @ResponseBody Object getPossibilityByStage(String stage){
        //解析配置文件
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(stage);
        return possibility;
    }
    @RequestMapping("/workbench/transaction/queryCustomerNameByName.do")
    public @ResponseBody Object queryCustomerNameByName(String customerName){
        List<String> stringList = customerService.queryCustomerNameByName(customerName);
        return stringList;
    }
    @RequestMapping("/workbench/transaction/saveCreateTran.do")
    public @ResponseBody Object saveCreateTran(@RequestParam Map<String, Object> map, HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();
        //处理参数
        map.put(Constant.SESSION_USER, user);
        try {
            tranService.saveCreateTran(map);
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后....");
        }
        return returnObject;
    }
    @RequestMapping("/workbench/transaction/detailTran.do")
    public String detailTran(String tranId, HttpServletRequest request){
        //调用service
        Tran tran = tranService.queryTranForDetailById(tranId);
        List<TranRemark> tranRemarkList = tranRemarkService.queryTranRemarkForDetailByTranId(tranId);
        List<TranHistory> historyList = tranHistoryService.queryTranHistoryForDetailByTranId(tranId);
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(tran.getStage());
        tran.setPossibility(possibility);
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        //添加到作用域
        request.setAttribute("tran", tran);
        request.setAttribute("tranRemarkList", tranRemarkList);
        request.setAttribute("historyList", historyList);
        request.setAttribute("stageList", stageList);
        return "workbench/transaction/detail";
    }
}
