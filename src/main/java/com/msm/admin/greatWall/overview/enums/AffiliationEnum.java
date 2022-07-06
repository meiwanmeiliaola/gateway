package com.msm.admin.greatWall.overview.enums;

import com.msm.admin.greatWall.gwNum.enums.AreaNameEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zxy
 * @date 2022/3/1 17:26
 */
@Getter
public enum AffiliationEnum {
    /*
     * 中国长城
     */
    CHINESEWALL("1","中国长城"),
    /*
     * 明长城
     */
    MINGWALL("2","明长城"),
    /*
     * 河北长城
     */
    HEBEIWALL("3","河北长城"),
    /*
     * 战国长城
     */
    WARWALL("4","战国长城");



    private final String id;
    private final String name;

    AffiliationEnum(String id,String name){
        this.id=id;
        this.name=name;
    }

    /*
     * 获取业务名称
     */
    public static String getName(String id){
        String name="未获取具体业务";
        if (StringUtils.isNotEmpty(id)){
            for (AffiliationEnum affiliationEnum:AffiliationEnum.values()){
                if (affiliationEnum.id.equalsIgnoreCase(id)){
                    return affiliationEnum.getName();
                }
            }
        }
        return name;
    }

}

