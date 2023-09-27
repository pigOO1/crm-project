package com.powernode.crm.settings.web.controller;

import com.powernode.crm.commons.constants.Constant;
import com.powernode.crm.commons.domain.ReturnObject;
import com.powernode.crm.commons.utils.DateUtils;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.settings.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        return "settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);

        //调用service层方法
        User user = userService.queryUserByLoginActAndPwd(map);

        //封装返回的json对象
        ReturnObject returnObject = new ReturnObject();
        //根据查询结果，处理响应
        if (user == null) {
            //登录失败，用户名或者密码错误
            returnObject.setCode("0");
            returnObject.setMessage("登录失败，用户名或者密码错误");
        }else {
            //其他原因
            if (DateUtils.forMateDateTime(new Date()).compareTo(user.getExpireTime()) > 0){
                //登录失败，账号已过期
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("登录失败，账号已过期");
            }else if(user.getLockState().equals(0)){
                //登录失败，账号已被锁定
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("登录失败，账号已被锁定");
            }else if (request.getRemoteAddr().contains(user.getAllowIps())){
                String remoteAddr = request.getRemoteAddr();
                String allowIps = user.getAllowIps();
                //登录失败，账号ip受限
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("登录失败，账号ip受限");
            }else {
                //登录成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);

                //保存到session域中
                session.setAttribute(Constant.SESSION_USER, user);

                //需要保存密码，写cookie
                if("true".equals(isRemPwd)){
                    Cookie cookie1 = new Cookie("loginAct", loginAct);
                    cookie1.setMaxAge(10 * 24 * 60 * 60);
                    response.addCookie(cookie1);

                    Cookie cookie2 = new Cookie("loginPwd", loginPwd);
                    cookie2.setMaxAge(10 * 24 * 60 * 60);
                    response.addCookie(cookie2);
                }else {
                    //杀死cookie
                    Cookie cookie1 = new Cookie("loginAct", "1");
                    cookie1.setMaxAge(0);
                    response.addCookie(cookie1);

                    Cookie cookie2 = new Cookie("loginPwd", "1");
                    cookie2.setMaxAge(0);
                    response.addCookie(cookie2);
                }
            }
        }
        return returnObject;
    }

    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response, HttpSession session){
        //杀死cookie
        Cookie cookie1 = new Cookie("loginAct", "1");
        cookie1.setMaxAge(0);
        response.addCookie(cookie1);

        Cookie cookie2 = new Cookie("loginPwd", "1");
        cookie2.setMaxAge(0);
        response.addCookie(cookie2);

        //销毁session
        session.invalidate();

        //重定向
        return "redirect:/";
    }

}
