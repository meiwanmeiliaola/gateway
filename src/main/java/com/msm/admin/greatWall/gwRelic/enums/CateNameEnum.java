package com.msm.admin.greatWall.gwRelic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zxy
 * @date 2022/3/1 16:41
 */
@Getter
public enum  CateNameEnum {

    /*
    * 碑刻
    */
    INSCRIPTION("5","碑刻"),
    /*
     * 兵器
     */
    WEAPON("1","兵器"),
    /*
     * 建筑构件
     */
    ARCHITECTURE("2","建筑构件"),
    /*
     * 造城工具
     */
    CITYTOOL("3","生产工具"),
    /*
     * 生活工具
     */
    LIFETOOL("4","生活工具"),
    /*
     * 其他
     */
    RESTS("6","其他");


    private final String id;
    private final String name;

    CateNameEnum(String id,String name){
        this.id=id;
        this.name=name;
    }

    /*
    * 获取业务id
    */
    public static String getName(String id){
        String name="未匹配到具体字段";
        if (StringUtils.isNotEmpty(id)){
            for (CateNameEnum cateNameEnum:CateNameEnum.values()){
                if (cateNameEnum.id.equalsIgnoreCase(id)){
                    return cateNameEnum.getName();
                }
            }
        }
        return name;
    }


}
