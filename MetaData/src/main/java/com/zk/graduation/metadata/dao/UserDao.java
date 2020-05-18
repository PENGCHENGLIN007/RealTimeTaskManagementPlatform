package com.zk.graduation.metadata.dao;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 用户信息操作
 *
 * @author pengchenglin
 * @create 2020-05-16 14:57
 */
@Slf4j
public class UserDao {
    /**
     * 获取所有的用户名
     * @return
     * @throws SQLException
     */
    public List<String> getAllUserName() throws SQLException {
        List<String> userNames = new LinkedList<>();
        String sql = "select NAME FROM USER_INFO ;";

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try{

        conn = MysqlDataSource.getConnection();
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while (rs.next()){
            userNames.add(rs.getString(1));
        }

        }catch (SQLException e){
            log.error("failed to execute dql:{}",e.toString());
            e.printStackTrace();
            throw e;

        }finally {
            MysqlDataSource.closeAll(conn,st,rs);

        }

        return userNames;
    }

    public boolean checkPassword(String userName,String password) throws SQLException {
        boolean result = false;
        String sql = "select * from user_info where name=? and password=?";

        Connection conn = null;
        PreparedStatement st= null;
        ResultSet rs = null;
        try{
            conn = MysqlDataSource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1,userName);
            st.setString(2,password);
            rs = st.executeQuery();
            while (rs.next()){
                result = true;
                log.info("用户名密码正确");
            }

        }catch (SQLException e){
            log.error("failed to execute dql:{}",e.toString());
            e.printStackTrace();
            throw e;
        }finally {
            MysqlDataSource.closeAll(conn,st,rs);

        }

        return result;

    }
}
