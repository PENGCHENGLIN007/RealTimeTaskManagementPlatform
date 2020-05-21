<%@ page import="com.zk.graduation.metadata.common.TaskInfo" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<% List<TaskInfo> taskInfoList = (List<TaskInfo>) request.getAttribute("taskList");

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<table border="1" height="40" width="1000" cellspacing="0" align="center">
    <tr align="center">
        <td>编号</td>
        <td>名称</td>
        <td>查看</td>
        <td>删除</td>
        <td>启动</td>
        <td>停止</td>
    </tr>
    <c:forEach items="${requestScope.taskList}" var="task">
    <tr align="center">
        <td>${task.taskId}</td>
        <td>${task.taskName}</td>
        <td><a href="">查看</a></td>
        <td><a href="">删除</a></td>
        <td><a href="/user/startTask.do?taskid=${task.taskId}">启动</a></td>
        <td><a href="">停止</a></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>