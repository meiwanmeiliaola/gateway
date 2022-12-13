package com.msm.admin.jdbc;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/5/5 18:07
 */
public class JDBCUtil {



    //1.加载驱动
    static{
        try {
            String driverClassName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //得到数据库的连接
    public static  Connection getConnection() throws SQLException {

        String url="
        String username = 
        String password = 


        return DriverManager.getConnection(url,username,password);
    }

    //关闭所有打开的资源
    public static void close(Connection conn, Statement stm){
        if (stm != null){
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //关闭所有打开的资源
    public static void close(Connection conn, Statement stm, ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stm != null){
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static HashMap<String,Object> sqlResult(String sql, List<String> keyList){
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;

        HashMap<String,Object> newMap=new HashMap<>(16);
        try {
            conn = getConnection();
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()){
                for (String s : keyList) {
                    String value = rs.getString(s);
                    newMap.put(s,value);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(conn,stm,rs);
        }

        return newMap;
    }


}
