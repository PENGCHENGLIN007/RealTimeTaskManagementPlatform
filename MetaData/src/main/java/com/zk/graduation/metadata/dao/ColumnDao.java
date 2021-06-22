package com.zk.graduation.metadata.dao;

import com.zk.graduation.metadata.common.ColumnInfo;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author 你的名字
 * @date 2020/5/22 0:06
 */
@Slf4j
public class ColumnDao {
    public static void insertColumn(ColumnInfo columnInfo){
        String sql = "insert into Column_Info(name,type,table_id,table_type,index)" +
                "values(?,?,?,?,?);";

        Connection conn = null;
        PreparedStatement st= null;

        try{
            conn = MysqlDataSource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1,columnInfo.getName());
            st.setString(2,columnInfo.getDataType());
            st.setInt(3,columnInfo.getTableId());
            st.setString(4,columnInfo.getTableType());
            st.setInt(5,columnInfo.getIndex());

            int count = st.executeUpdate();
            if(count!=1){
                log.error("insert into Column_Info error");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            MysqlDataSource.closeAll(conn,st,null);
        }
    }

}
