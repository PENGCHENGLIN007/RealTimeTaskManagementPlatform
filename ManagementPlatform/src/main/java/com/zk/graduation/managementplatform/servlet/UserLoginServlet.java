package com.zk.graduation.managementplatform.servlet;

import com.zk.graduation.managementplatform.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 */
@Slf4j
@WebServlet(name = "/user/login",urlPatterns={"/user/login.do"})
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String userName = request.getParameter("userName");
        String password = request.getParameter("userPassword");
        log.info("userName:{}",userName);
        log.info("password:{}",password);

        try{
            new UserLoginService().userLogin(userName,password);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            log.info("登录失败：{}",errorMessage);
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setHeader("refresh", "0;url=/userLogin.html?errorMessage="+java.net.URLEncoder.encode(errorMessage,"UTF-8"));

            return;
        }


        response.sendRedirect("/userMain.html");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
