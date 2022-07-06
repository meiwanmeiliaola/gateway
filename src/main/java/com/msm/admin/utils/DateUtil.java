package com.msm.admin.utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zxy
 * @date 2022/2/9 17:08
 */
public class DateUtil {

    /**
    * 获取当前时间  yyyy-MM-dd hh:mm:ss
     */
    public static String getNowTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

}
