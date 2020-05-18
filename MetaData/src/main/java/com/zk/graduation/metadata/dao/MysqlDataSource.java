package com.zk.graduation.metadata.dao;

import lombok.extern.slf4j.Slf4j;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据源
 *
 * @author pengchenglin
 * @create 2020-05-16 14:46
 */
@Slf4j
public class MysqlDataSource {
    /**
     * 获取mysql数据库连接
     * @return Connection
     */
    public static Connection getConnection(){
        Context initContext;
        Context envContext;
        Connection con = null;
        try {
            initContext = new InitialContext();
            envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/MysqlSource");
            con = ds.getConnection();
            log.info("get database connection successfully!");
        } catch (NamingException e) {
            log.error("failed to initialize mysql data source!");
            e.printStackTrace();
        } catch (SQLException e) {
            log.error("failed to get mysql connection!");
            e.printStackTrace();
        }

        return con;
    }

    public static void closeAll(Connection conn, Statement statement, ResultSet resultSet){

        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            log.error("failed to close");
            e.printStackTrace();
        }
    }

}
