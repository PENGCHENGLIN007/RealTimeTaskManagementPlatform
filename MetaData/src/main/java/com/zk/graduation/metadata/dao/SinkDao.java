package com.zk.graduation.metadata.dao;

import com.zk.graduation.metadata.common.SinkInfo;
import com.zk.graduation.metadata.common.SourceInfo;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 输出源信息表
 *
 * @author pengchenglin
 * @create 2020-05-19 19:12
 */
@Slf4j
public class SinkDao {

    public static SinkInfo getSinkInfo(int taskId){
        SinkInfo sinkInfo = null;
        return sinkInfo;
    }

    public static void createSink(SinkInfo sinkInfo) throws SQLException {

        String sql = "insert into sink_info(name,type,user_id,task_id,parameter)" +
                "values(?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement st= null;

        try{
            conn = MysqlDataSource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1,sinkInfo.getName());
            st.setString(2,sinkInfo.getSourceType().toString());
            st.setInt(3,sinkInfo.getUserId());
            st.setInt(4,sinkInfo.getTaskId());
            st.setString(5,sinkInfo.getParam());
            int count = st.executeUpdate();
            if(count!=1){
                log.error("insert into sink_info error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            MysqlDataSource.closeAll(conn,st,null);
        }

    }

    public static int getIdByName(String sinkName) throws SQLException {
        int sinkId  = 0;

        String sql = "select ID from sink_info where name=? ;";

        Connection conn = null;
        PreparedStatement st= null;
        ResultSet rs = null;
        try{
            conn =  MysqlDataSource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1,sinkName);
            rs = st.executeQuery();
            while (rs.next()){
                sinkId = rs.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            MysqlDataSource.closeAll(conn,st,rs);
        }

        return sinkId;
    }
}
