package com.powernode.crm.settings.service;

import com.powernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 根据用户登录名和密码查询用户信息
 *
 * @return
 */
public interface UserService {
    User queryUserByLoginActAndPwd(Map<String, Object> map);

    List<User> queryAllUsers();
}
