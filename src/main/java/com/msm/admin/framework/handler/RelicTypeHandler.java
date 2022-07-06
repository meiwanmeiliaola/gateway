//package com.msm.admin.framework.handler;
//
//import com.msm.admin.modules.relic.RelicTypeEnum;
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.MappedJdbcTypes;
//import org.apache.ibatis.type.MappedTypes;
//import org.springframework.util.StringUtils;
//
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author quavario@gmail.com
// * @date 2019/7/2 11:47
// */
//@MappedJdbcTypes({JdbcType.VARCHAR})
//@MappedTypes({List.class})
//public class RelicTypeHandler extends BaseTypeHandler<RelicTypeEnum> {
//
//
//    @Override
//    public void setNonNullParameter(PreparedStatement preparedStatement, int i, RelicTypeEnum relicType, JdbcType jdbcType) throws SQLException {
//
//        //数组转成字符串，以，分割
//        preparedStatement.setInt(i, relicType.getCode());
//    }
//
//    @Override
//    public RelicTypeEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
//        int anInt = resultSet.getInt(s);
//        RelicTypeEnum value = RelicTypeEnum.values()[anInt];
//        return value;
//    }
//
//    @Override
//    public RelicTypeEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
//        int anInt = resultSet.getInt(i);
//        RelicTypeEnum value = RelicTypeEnum.values()[anInt];
//        return value;
//    }
//
//    @Override
//    public RelicTypeEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
//        int anInt = callableStatement.getInt(i);
//        RelicTypeEnum value = RelicTypeEnum.values()[anInt];
//        return value;
//    }
//}
