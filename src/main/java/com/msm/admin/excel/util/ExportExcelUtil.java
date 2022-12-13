package com.msm.admin.excel.util;

import com.msm.admin.excel.entity.RelicExcelEntity;
import com.msm.admin.excel.entity.RelicExcelSubsetEntity;
import com.msm.admin.excel.enums.RelicExcelEnum;
import com.msm.admin.greatWall.gwRelic.entity.GwRelic;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExportExcelUtil {


    public static List<RelicExcelSubsetEntity> excelExport(List<GwRelic> relicList,List<String> choseType) throws Exception{

        List<RelicExcelSubsetEntity> subsetEntityList;

        Map<String, Object> excelCode = RelicExcelEnum.getExcelCode(choseType);

        subsetEntityList=entities(relicList,excelCode);

        return subsetEntityList;
    }

    public static List<List<String>> head(List<String> choseType){
        Map<String, Object> excelCode = RelicExcelEnum.getExcelCode(choseType);

        List<List<String>> list = new ArrayList<List<String>>();


        Set<String> stringSet = excelCode.keySet();

        for (String s : stringSet) {
            List<String> head0 = new ArrayList<String>();
            String o = excelCode.get(s).toString();
            head0.add("主标题");
            head0.add(o);
            list.add(head0);
        }

        return list;

    }

    public static List<RelicExcelSubsetEntity> entities(List<GwRelic> relicList,Map<String,Object> excelCode) throws Exception {

        List<RelicExcelSubsetEntity> entities=new ArrayList<>();

        for (GwRelic gwRelic : relicList) {
            RelicExcelSubsetEntity subsetEntity=new RelicExcelSubsetEntity();
            if ("名称".equals(excelCode.get("name"))){
                String name = gwRelic.getName();
                subsetEntity.setName(name);
            }
            if ("年代".equals(excelCode.get("yearName"))){
                String yearName = gwRelic.getYearName();
                subsetEntity.setYearName(yearName);
            }
            if ("材质".equals(excelCode.get("materialName"))){
                String materialName=gwRelic.getMaterialName();
                subsetEntity.setMaterialName(materialName);
            }
            if ("部门".equals(excelCode.get("depName"))){
                String depName=gwRelic.getDepName();
                subsetEntity.setDepName(depName);
            }
            if ("图片".equals(excelCode.get("thumb"))){
                String uri="http://47.92.231.181:9002";
                String thumb = gwRelic.getThumb();
                subsetEntity.setThumb(new URL(uri+thumb));
            }
            if ("类别".equals(excelCode.get("categoryName"))){
                String categoryName=gwRelic.getCategoryName();
                subsetEntity.setCategoryName(categoryName);
            }
            if ("工艺等级".equals(excelCode.get("pql"))){
                String pql=gwRelic.getPql();
                subsetEntity.setPql(pql);
            }
            entities.add(subsetEntity);
        }

        return entities;

    }


}
