package com.msm.admin.excel.enums;

import java.util.*;

public enum RelicExcelEnum {

    NAME_EXCEL("1","name","名称"),

    YEAR_NAME_EXCEL("2","yearName","年代"),

    MATERIAL_NAME("3","materialName","材质"),

    DEP_NAME_EXCEL("4","depName","部门"),

    THUMB_EXCEL("5","thumb","图片"),

    CATEGORY_EXCEL("6","categoryName","类别"),

    PQL_EXCEL("7","pql","工艺等级");



    private final String code;

    private final String name;

    private final String header;

    RelicExcelEnum(String code,String name,String header){
        this.code=code;
        this.name=name;
        this.header=header;
    }

    public static Map<String,Object> getExcelCode(List<String> codes){

        Map<String,Object> codeMap=new LinkedHashMap<>();

        for (RelicExcelEnum excelEnum:RelicExcelEnum.values()){

            codes.forEach(s -> {
                boolean flag = excelEnum.code.equals(s);
                if (flag){
                    codeMap.put(excelEnum.name,excelEnum.header);
                }
            });
        }
        return codeMap;
    }

    public static Set<String> includeColumnFiledNames(List<String> codes){
        Set<String> includeColumnFiledNames = new HashSet<String>();

        for (RelicExcelEnum excelEnum:RelicExcelEnum.values()){

            codes.forEach(s -> {
                boolean flag = excelEnum.code.equals(s);
                if (flag){
                    includeColumnFiledNames.add(excelEnum.name);
                }
            });
        }
        return includeColumnFiledNames;

    }


}
