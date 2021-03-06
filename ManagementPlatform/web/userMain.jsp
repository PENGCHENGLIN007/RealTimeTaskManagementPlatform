<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String userName = (String) session.getAttribute("userName");
if(userName==null) userName="";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style type='text/css'>
        /*下面的星号让素有元素内外边距都为0，不留空隙*/
        *{
            padding:0px;
            margin:0px;
            /*我们让整个屏幕都有一种淡淡的背景色*/
        }
        body{
            background-color:lightcyan;
        }
        /*下面这个box是承载全部网页内容的容器样式，是下面所有div元素的父级元素的样式，注意一定要先将字号设为0，否则主体和页脚之间会有一条白色缝隙*/
        #box{
            width:100%;
            margin:0 auto;
            font-size:0;
        }
        #logo{
            height:60px;
            background-color: #9dd7ff;
        }
        #nav{
            display:inline-block;
            width:10%;
            height:600px;
            background-color:lightseagreen;

        }
        #main{
            display:inline-block;
            width:90%;
            height:600px;
            background-color:#999;
            float:right;

        }
        #footer{
            height:40px;
            background-color:darkgrey;

        }
    </style>
</head>
<body>

<div id='box'>
    <div id='logo'>
        <span style="font-size:40px;margin-left:100px;margin-top: 20px">实时计算任务管理平台</span>
        <span style="font-size:15px;margin-left:900px;margin-top: 20px;color: blue;"><%=userName%></span>
    </div>
    <div id='nav'>
        <ul type='none' style="font-size:20px;margin-left:0px;margin-top:100px;">
            <li style="border:2px solid  #64d6ff;"><a style="text-decoration:none;" href="/user/getAllTask.do" target="iframe_main">全部任务</a></li>
            <li style="border:2px solid  #64d6ff;"><a style="text-decoration:none;"  href="createTask.html" target="iframe_main">创建任务</a></li>
            <li style="border:2px solid  #64d6ff;"><a style="text-decoration:none;"  href="runningTaskList.html" target="iframe_main">运行中任务</a></li>
        </ul>
    </div>
    <div id='main'>
        <iframe  name="iframe_main" scrolling="no" frameborder="0"  width="100%" height="600px"/>
    </div>

    <div id='footer'>
    </div>
</div>

</body>
</html>