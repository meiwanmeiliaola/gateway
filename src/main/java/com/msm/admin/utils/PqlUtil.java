package com.msm.admin.utils;

import com.msm.admin.greatWall.gwCulture.entity.CultureCategory;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

/**
 * @author zxy
 * @date 2022/2/11 9:53
 */
public class PqlUtil {

    public static List<Map<String,String>> pqlList(){
        List<String> stringList=Arrays.asList("精美","普通");
        List<Map<String,String>> list=new LinkedList<>();
        for (int i = 0; i < stringList.size(); i++) {
            Map<String,String> map=new HashMap<>();
            Integer num=i+1;
            map.put("label",stringList.get(i));
            map.put("value",num.toString());
            map.put("sort",num.toString());
            map.put("type","pql");
            list.add(map);
        }

        return list;
    }


    public static List<Map<String,String>> list(){
        List<String> stringList= Arrays.asList("兵器","建筑构件","生产工具","生活用具","碑刻","其他");
        List<Map<String,String>> list=new LinkedList<>();
        for (int i = 0; i < stringList.size(); i++) {
            Map<String,String> map=new HashMap<>();
            Integer num=i+1;
            map.put("label",stringList.get(i));
            map.put("value",num.toString());
            map.put("sort",num.toString());
            map.put("type","GwCate");
            list.add(map);
        }
        return list;
    }





}
