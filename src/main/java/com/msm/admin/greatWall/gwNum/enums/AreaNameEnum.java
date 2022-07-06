package com.msm.admin.greatWall.gwNum.enums;

import com.msm.admin.greatWall.gwCulture.enums.CategoryNameEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zxy
 * @date 2022/3/1 17:20
 */
@Getter
public enum AreaNameEnum {

    /*
     * 秦皇岛
     */
    QINHUANGDAO("1","秦皇岛"),
    /*
     * 唐山
     */
    TANGSHAN("2","唐山"),
    /*
     * 承德
     */
    CHENGDE("3","承德"),
    /*
     * 张家口"
     */
    ZHANGJIAKOU("4","张家口"),
    /*
     * 保定"
     */
    BAODING("5","保定");


    private final String id;
    private final String name;

    AreaNameEnum(String id,String name){
        this.id=id;
        this.name=name;
    }

    /*
     * 获取业务名称
     */
    public static String getName(String id){
        String name="未获取具体业务";
        if (StringUtils.isNotEmpty(id)){
            for (AreaNameEnum areaNameEnum:AreaNameEnum.values()){
                if (areaNameEnum.id.equalsIgnoreCase(id)){
                    return areaNameEnum.getName();
                }
            }
        }
        return name;
    }

}
