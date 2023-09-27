package com.powernode.crm.workbench.web.controller;

import com.powernode.crm.commons.constants.Constant;
import com.powernode.crm.commons.domain.ReturnObject;
import com.powernode.crm.commons.utils.DateUtils;
import com.powernode.crm.commons.utils.HSSFUtils;
import com.powernode.crm.commons.utils.UUIDUtils;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.settings.service.UserService;
import com.powernode.crm.workbench.domain.Activity;
import com.powernode.crm.workbench.domain.ActivityRemark;
import com.powernode.crm.workbench.service.ActivityRemarkService;
import com.powernode.crm.workbench.service.ActivityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Controller
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        //查询所有的用户信息
        List<User> userList = userService.queryAllUsers();
        //把数据保存到request中
        request.setAttribute("userList", userList);
        //跳转
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/savaCreateActivity.do")
    @ResponseBody
    public Object savaCreateActivity(Activity activity, HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        activity.setCreateBy(user.getId());
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.forMateDateTime(new Date()));

        ReturnObject returnObject = new ReturnObject();
        try{
            int ret = activityService.savaCreateActivity(activity);
            if(ret > 0){
                //成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙中，请稍后.......");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙中，请稍后.......");
        }
        return returnObject;

    }

    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    @ResponseBody
    public Object queryActivityByConditionForPage(String name, String owner, String startDate, String endDate,
                                                  int pageNo, int pageSize){
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);

        //查询数据
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        int totalRows = activityService.queryCountOfActivityByCondition(map);

        //封装数据
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("activityList", activityList);
        retMap.put("totalRows", totalRows);

        //响应请求
        return retMap;
    }

    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    public @ResponseBody Object deleteActivityByIds(String[] id){
        ReturnObject returnObject = new ReturnObject();
        try{
            int record = activityService.deleteActivityByIds(id);
            if(record > 0){
                //删除成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试.....");
            }

        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试.....");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    public @ResponseBody Object queryActivityById(String id){
        Activity activity = activityService.queryActivityById(id);
        return activity;
    }

    @RequestMapping("/workbench/activity/saveEditActivity.do")
    public @ResponseBody Object saveEditActivity(Activity activity, HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        try{
            //封装参数
            activity.setEditBy(user.getId());
            activity.setEditTime(DateUtils.forMateDateTime(new Date()));
            //更新数据
            int record = activityService.saveEditActivity(activity);
            if(record > 0){
                //删除成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试.....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试.....");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/exportAllActivities.do")
    public void exportAllActivities(HttpServletResponse response) throws Exception{
        //调用service查询所有市场活动
        List<Activity> activityList = activityService.queryAllActivities();
        //创建wb
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建页
        HSSFSheet sheet = wb.createSheet("市场活动列表");
        //创建行
        HSSFRow row = sheet.createRow(0);
        //创建第一行单元格
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");

        cell = row.createCell(1);
        cell.setCellValue("所有者");

        cell = row.createCell(2);
        cell.setCellValue("名称");

        cell = row.createCell(3);
        cell.setCellValue("开始日期");

        cell = row.createCell(4);
        cell.setCellValue("结束日期");

        cell = row.createCell(5);
        cell.setCellValue("成本");

        cell = row.createCell(6);
        cell.setCellValue("描述");

        cell = row.createCell(7);
        cell.setCellValue("创建时间");

        cell = row.createCell(8);
        cell.setCellValue("创建者");

        cell = row.createCell(9);
        cell.setCellValue("修改时间");

        cell = row.createCell(10);
        cell.setCellValue("修改者");

        if(activityList != null && activityList.size() > 0){
            Activity activity = null;
            for(int i = 0; i < activityList.size(); i ++){

                activity = activityList.get(i);
                //每创建一个市场活动对象，就创建一行
                row = sheet.createRow(i + 1);
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());

                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());

                cell = row.createCell(2);
                cell.setCellValue(activity.getName());

                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());

                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());

                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());

                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());

                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());

                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());

                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());

                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());

            }
        }

//        OutputStream os = new FileOutputStream("E:\\javanote\\project\\ssmCRM\\serverDir\\activities.xls");
//        //创建excel
//        wb.write(os);
//
//        //关闭资源
//        os.close();
//        wb.close();

        //把生成的excel文件下载到浏览器
        response.setContentType("application/octet-stream;charset=UTF-8");
        //设置响应头信息
        response.addHeader("Content-Disposition","attachment;filename=activities.xls");
        OutputStream out = response.getOutputStream();
        byte[] buff = new byte[255];
//        FileInputStream is = new FileInputStream("E:\\javanote\\project\\ssmCRM\\serverDir\\activities.xls");
//        int len = 0;
//        while((len = is.read(buff)) != -1) {
//            out.write(buff, 0, len);
//        }
//        is.close();
        wb.write(out);
        wb.close();
        out.flush();
    }

    @RequestMapping("/workbench/activity/querySelectActivities.do")
    public void querySelectActivities(HttpServletResponse response, String[] id) throws Exception{
        //调用service查询所有市场活动
        List<Activity> activityList = activityService.querySelectActivities(id);
        //创建wb
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建页
        HSSFSheet sheet = wb.createSheet("市场活动列表");
        //创建行
        HSSFRow row = sheet.createRow(0);
        //创建第一行单元格
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("成本");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");

        if(activityList != null && activityList.size() > 0){
            Activity activity = null;
            for(int i = 0; i < activityList.size(); i ++){

                activity = activityList.get(i);
                //每创建一个市场活动对象，就创建一行
                row = sheet.createRow(i + 1);
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());

            }
        }
        //把生成的excel文件下载到浏览器
        response.setContentType("application/octet-stream;charset=UTF-8");
        //设置响应头信息
        response.addHeader("Content-Disposition","attachment;filename=activities.xls");
        OutputStream out = response.getOutputStream();
        byte[] buff = new byte[255];

        wb.write(out);
        wb.close();
        out.flush();
    }

    @RequestMapping("/workbench/activity/importActivities.do")
    public @ResponseBody Object importActivities(MultipartFile activityFile, HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();
        InputStream is = null;
        HSSFWorkbook wb = null;
        try {
            //将activityFile写到磁盘中
            //得到文件名
//            String originalFilename = activityFile.getOriginalFilename();
//            //磁盘中的文件
//            File file = new File("E:\\javanote\\project\\ssmCRM\\serverDir", originalFilename);
//            activityFile.transferTo(file);

            //解析excel文件
//            is = new FileInputStream(file);
            //拿到wb
            is = activityFile.getInputStream();
            wb = new HSSFWorkbook(is);
            //获取页
            HSSFSheet sheet = wb.getSheetAt(0);
            //循环获取行列
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            List<Activity> activities = new ArrayList<>();
            for(int i = 1; i <= sheet.getLastRowNum(); i ++){//不获取表头，索引从1开始
                //获取行
                row = sheet.getRow(i);
                //封装市场活动对象
                activity = new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setCreateBy(user.getId());
                activity.setCreateTime(DateUtils.forMateDateTime(new Date()));
                activity.setOwner(user.getId());
                for(int j = 0; j < row.getLastCellNum(); j ++){//row.getLastCellNum():最后一列加一（总列数）
                    //获取列
                    cell = row.getCell(j);
                    //获取value值
                    String value = HSSFUtils.getCellValueByStr(cell);
                    if(j == 0){
                        activity.setName(value);
                    }else if (j == 1){
                        activity.setStartDate(value);
                    } else if (j == 2) {
                        activity.setEndDate(value);
                    } else if (j == 3) {
                        activity.setCost(value);
                    } else if (j == 4) {
                        activity.setDescription(value);
                    }
                }
                //保存所有的市场活动对象
                activities.add(activity);

            }
            //调用service层
            int record = activityService.saveCreateActivitiesByList(activities);
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setRetData(record);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙,请稍后....");
        }finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return returnObject;
        }
    }

    @RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id, HttpServletRequest request){
        //调用service方法，查询
        Activity activity = activityService.queryActivityForDetailById(id);
        List<ActivityRemark> activityRemarkList = activityRemarkService.queryActivityDetailByActivityId(id);
        //封装，添加到作用域里
        request.setAttribute("activity", activity);
        request.setAttribute("activityRemarkList", activityRemarkList);
        //请求转发
        return "workbench/activity/detail";
    }
}
