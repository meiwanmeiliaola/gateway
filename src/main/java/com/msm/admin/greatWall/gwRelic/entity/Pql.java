package com.msm.admin.greatWall.gwRelic.entity;

import lombok.Data;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/3/14 22:02
 */
@Data
public class Pql {

    private String id;

    private String name;

    public static List<Pql> pqlList(){
        List<String> strings = Arrays.asList("精美", "普通");

        List<Pql> pqlList=new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            Integer num=i+1;
            Pql pql=new Pql();
            pql.setId(num.toString());
            pql.setName(strings.get(i));
            pqlList.add(pql);
        }
        return pqlList;
    }

    public static List<Pql> sortList(){
        List<String> strings = Arrays.asList("置顶", "不置顶");

        List<Pql> pqlList=new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            Integer num=i+1;
            Pql pql=new Pql();
            pql.setId(num.toString());
            pql.setName(strings.get(i));
            pqlList.add(pql);
        }
        return pqlList;
    }
}
