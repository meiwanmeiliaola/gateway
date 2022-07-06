package com.msm.admin.jdbc;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/5/5 17:58
 */
public class test {


    public static void main(String[] args){

        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConnection();
            stm = conn.createStatement();
            rs = stm.executeQuery("select * from gw_culture where id= '053f3b2553fe88a28d7586f2ba0e0626'");
            while (rs.next()){
                System.out.println("主键：" + rs.getString("id") +
                        ",标题：" + rs.getString("title") +
                        ",移动端缩略图：" + rs.getString("min_thumb") +
                        ",压缩包：" + rs.getDate("zip_url") +
                        ",pc端缩略图：" + rs.getInt("max_thumb"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(conn,stm,rs);
        }
    }

}
