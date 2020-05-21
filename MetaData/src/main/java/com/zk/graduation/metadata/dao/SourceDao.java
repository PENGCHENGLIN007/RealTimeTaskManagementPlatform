package com.zk.graduation.metadata.dao;

import com.zk.graduation.metadata.common.SourceInfo;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 输入源信息管理
 *
 * @author pengchenglin
 * @create 2020-05-19 19:12
 */
@Slf4j
public class SourceDao {
    public static SourceInfo getSourceInfo(int taskId){
        SourceInfo sourceInfo = null;
        return  sourceInfo;
    }

    public static void createSource(SourceInfo sourceInfo) throws SQLException {

        String sql = "insert into source_info(name,type,user_id,task_id,parameter)" +
                "values(?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement st= null;

        try{
            conn = MysqlDataSource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1,sourceInfo.getName());
            st.setString(2,sourceInfo.getSourceType().toString());
            st.setInt(3,sourceInfo.getUserId());
            st.setInt(4,sourceInfo.getTaskId());
            st.setString(5,sourceInfo.getParam());
            int count = st.executeUpdate();
            if(count!=1){
                log.error("insert into source_info error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            MysqlDataSource.closeAll(conn,st,null);
        }
    }

    public static int getIdByName(String sourceName) throws SQLException {
        int sourceId  = 0;

        String sql = "select ID from source_info where name=? ;";

        Connection conn = null;
        PreparedStatement st= null;
        ResultSet rs = null;
        try{
            conn =  MysqlDataSource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1,sourceName);
            rs = st.executeQuery();
            while (rs.next()){
                sourceId = rs.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            MysqlDataSource.closeAll(conn,st,rs);
        }

        return sourceId;
    }
}
