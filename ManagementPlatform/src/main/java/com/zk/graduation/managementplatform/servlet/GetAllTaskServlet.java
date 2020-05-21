package com.zk.graduation.managementplatform.servlet;

import com.zk.graduation.managementplatform.service.GetAllTaskService;
import com.zk.graduation.metadata.common.TaskInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 你的名字
 * @date 2020/5/22 3:27
 */
@Slf4j
@WebServlet(name = "/user/getAllTask",urlPatterns={"/user/getAllTask.do"})
public class GetAllTaskServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<TaskInfo> taskInfos = null;
        try {
            taskInfos = new GetAllTaskService().execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("taskList",taskInfos);
        request.getRequestDispatcher("/allTaskList.jsp").forward(request,response);
    }


    }
