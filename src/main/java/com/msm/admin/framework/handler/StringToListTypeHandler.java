package com.msm.admin.framework.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 自动转换处理器
 * 插入数据库前自动将list转换成string，用于多图上传，list<String>类型转换成string,
 * 在mybatis注册此typehandler后即可自动转换
 * 参考：
 *      https://blog.csdn.net/u012702547/article/details/54572679
 *      https://my.oschina.net/amoshuang/blog/134199
 *      https://github.com/baomidou/mybatis-plus/issues/357
 * @author quavario@gmail.com
 * @date 2019/7/2 11:47
 */
@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({List.class})
public class StringToListTypeHandler extends BaseTypeHandler<List<String>> {


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {

        //数组转成字符串，以，分割
        preparedStatement.setString(i, strings.size() == 0 ? null : String.join(",", strings));
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String images = resultSet.getString(s);
        if (!StringUtils.isEmpty(images)) {
            return Arrays.asList(images.split(","));
        }
        return null;
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return Arrays.asList(resultSet.getString(i).split(","));
    }

    @Override
    public List<String> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return Arrays.asList(callableStatement.getString(i).split(","));
    }
}
