package com.msm.admin.greatWall.gwCulture.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zxy
 * @date 2022/3/1 17:09
 */
@Getter
public enum CategoryNameEnum {

    /*
    * 历史故事
    */
    HISTORICALSTORY("1","历史故事"),
    /*
     * 神话传说
     */
    MYTHOLOGY("2","神话传说"),
    /*
     * 人物传奇
     */
    BIOGRAPHY("3","人物传奇"),
    /*
     * 长城碑刻"
     */
    INSCRIPTION("4","长城碑刻");


    private final String id;
    private final String name;

    CategoryNameEnum(String id,String name){
        this.id=id;
        this.name=name;
    }

    /*
    * 获取业务名称
    */
    public static String getName(String id){
        String name="未获取具体业务";
        if (StringUtils.isNotEmpty(id)){
            for (CategoryNameEnum categoryNameEnum:CategoryNameEnum.values()){
                if (categoryNameEnum.id.equalsIgnoreCase(id)){
                    return categoryNameEnum.getName();
                }
            }
        }
        return name;
    }

}
