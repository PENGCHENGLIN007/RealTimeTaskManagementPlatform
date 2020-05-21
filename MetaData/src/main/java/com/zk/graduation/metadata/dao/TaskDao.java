package com.zk.graduation.metadata.dao;

import com.zk.graduation.metadata.common.TaskInfo;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * 任务信息管理
 *
 * @author pengchenglin
 * @create 2020-05-19 17:56
 */
@Slf4j
public class TaskDao {

    public static TaskInfo getTaskInfo(int taskId ){
        TaskInfo taskInfo = null;
        return taskInfo;
    }

    public static List<TaskInfo> getAll() throws SQLException {
        List<TaskInfo> taskInfos = new LinkedList<>();
        String sql = "select * from task_info;";
        Connection conn = null;
        PreparedStatement st= null;
        ResultSet rs = null;

        try{
            conn =  MysqlDataSource.getConnection();
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int userId = rs.getInt(3);
                String flinkid = rs.getString(4);
                String taskSql = rs.getString(5);
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.setTaskId(id);
                taskInfo.setTaskName(name);
                taskInfo.setUserId(userId);
                taskInfo.setSql(taskSql);
                taskInfos.add(taskInfo);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            MysqlDataSource.closeAll(conn,st,rs);
        }

        return taskInfos;
    }

    /**
     * 检查任务名称是否存在
     * @return
     */
    public static boolean checkTaskNameExist(String taskName) throws SQLException {
        boolean exist = false;

        String sql = "select * from task_info where name=? ;";

        Connection conn = null;
        PreparedStatement st= null;
        ResultSet rs = null;
        try{
            conn =  MysqlDataSource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1,taskName);
            rs = st.executeQuery();
            while (rs.next()){
                exist = true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            MysqlDataSource.closeAll(conn,st,rs);
        }

        return exist;
    }

    public static void createTask(TaskInfo taskInfo) throws SQLException {
        String sql = "insert into task_info(name,user_id,`sql`)" +
                "values(?,?,?);";
        Connection conn = null;
        PreparedStatement st= null;
        ResultSet rs = null;
        //todo print sql
        System.out.println(taskInfo.getSql());

        try{
            conn =  MysqlDataSource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1,taskInfo.getTaskName());
            st.setInt(2,taskInfo.getUserId());
            st.setString(3,taskInfo.getSql());
            int result = st.executeUpdate();
            if(result!=1){
                log.error("insert into task_info error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            MysqlDataSource.closeAll(conn,st,rs);
        }
    }

    public static int getIdByName(String taskName) throws SQLException {
        int taskId  = 0;

        String sql = "select ID from task_info where name=? ;";

        Connection conn = null;
        PreparedStatement st= null;
        ResultSet rs = null;
        try{
            conn =  MysqlDataSource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1,taskName);
            rs = st.executeQuery();
            while (rs.next()){
                taskId = rs.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            MysqlDataSource.closeAll(conn,st,rs);
        }

        return taskId;
    }


}
