package com.msm.admin.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json对象互转工具类
 * @author quavario@gmail.com
 * @date 2019/9/4 10:46
 */
public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * json to list
     * @param jsonStr json字符串
     * @param valueTypeRef 数组类型 如new TypeReference<List<User>>
     * @param <T> 对象类型
     * @return 返回对象
     */
    public static <T> T jsonToList(String jsonStr, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     * (1)转换为普通JavaBean：jsonToEntity(json,Student.class)
     * (2)转换为List,如List<Student>,将第二个参数传递为Student
     * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
     *
     * @param jsonStr
     * @param valueType
     * @return
     */
    public static <T> T jsonToEntity(String jsonStr, Class<T> valueType) {

        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static <T> String objectToJson(T obj) {
        if (obj == null) {
            return null;
        }
        String json = null;
        try {
            json = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
