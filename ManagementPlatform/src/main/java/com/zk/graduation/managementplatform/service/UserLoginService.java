package com.zk.graduation.managementplatform.service;

import com.zk.graduation.metadata.dao.UserDao;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * 用户登录服务
 *
 * @author pengchenglin
 * @create 2020-05-16 14:25
 */
@Slf4j
public class UserLoginService {
    public void userLogin(String userName,String password) throws Exception {
        UserDao userDao = new UserDao();
        List<String> userNameList;
        try{
            userNameList = userDao.getAllUserName();
            if(!userNameList.contains(userName)){
                log.info("用户名{}不存在",userName);
                throw new Exception("用户名或密码错误");
            }
            if(!userDao.checkPassword(userName,password)){
                log.info("用户名{}密码错误",userName);
                throw new Exception("用户名或密码错误");
            }

        }catch (SQLException e){

            throw new Exception("系统错误");
        }
    }


}
