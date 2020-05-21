package com.zk.graduation.managementplatform.servlet;

import com.zk.graduation.managementplatform.service.CreateTaskService;
import com.zk.graduation.metadata.common.SinkInfo;
import com.zk.graduation.metadata.common.SourceInfo;
import com.zk.graduation.metadata.common.SourceType;
import com.zk.graduation.metadata.common.TaskInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 创建任务Servlet
 *
 * @author pengchenglin
 * @create 2020-05-21 19:09
 */
@Slf4j
@WebServlet(name = "/user/createTask",urlPatterns={"/user/createTask.do"})
public class CreateTaskServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");

        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();

        if(userName==null ||"".equals(userName)){
            log.error("failed to create task, please login first! ");
            writer.println("创建失败！请先登录！");
            writer.flush();
            writer.close();
            return;
            //response.sendRedirect("/userLogin.html");
        }
        


        String sourceName = request.getParameter("sourceName");
        String sourceType = request.getParameter("sourceType");
        String sourceFields = request.getParameter("sourceFields");
        String sourceProperties = request.getParameter("sourceProperties");

        SourceInfo sourceInfo = new SourceInfo();
        sourceInfo.setName(sourceName);
        sourceInfo.setSourceType(SourceType.valueOf(sourceType));
        sourceInfo.setParam(sourceProperties);
        sourceInfo.setSchema(sourceFields);

        String sinkName = request.getParameter("sinkName");
        String sinkType = request.getParameter("sinkType");
        String sinkFields = request.getParameter("sinkFields");
        String sinkProperties = request.getParameter("sinkProperties");

        SinkInfo sinkInfo = new SinkInfo();
        sinkInfo.setName(sinkName);
        sinkInfo.setSourceType(SourceType.valueOf(sinkType));
        sinkInfo.setParam(sinkProperties);
        sinkInfo.setSchema(sinkFields);

        String taskName = request.getParameter("taskName");
        String taskSql = request.getParameter("taskSql");

        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setSourceInfo(sourceInfo);
        taskInfo.setSinkInfo(sinkInfo);
        taskInfo.setTaskName(taskName);
        taskInfo.setSql(taskSql);



        try{
            new CreateTaskService().execute(taskInfo);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            log.info("创建失败：{}",errorMessage);
            writer.println("创建失败！"+e.toString());
            writer.flush();
            writer.close();
            return;
        }
        writer.println("创建成功！");
        writer.flush();
        writer.close();


       // response.sendRedirect("/allTaskList.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
