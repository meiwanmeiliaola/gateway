package com.msm.admin.excel.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.excel.entity.RelicExcelSubsetEntity;
import com.msm.admin.excel.enums.RelicExcelEnum;
import com.msm.admin.excel.util.ExportExcelUtil;
import com.msm.admin.greatWall.gwCollection.entity.CollectionEntity;
import com.msm.admin.greatWall.gwCollection.service.CollectionService;
import com.msm.admin.greatWall.gwRelic.entity.GwRelic;
import com.msm.admin.greatWall.gwRelic.enums.CateNameEnum;
import com.msm.admin.greatWall.gwRelic.mapper.GwRelicServiceMapper;
import com.msm.admin.greatWall.gwRoam.ImageDemoData;
import com.msm.admin.modules.common.entity.Years;
import com.msm.admin.modules.common.mapper.YearsMapper;
import com.msm.admin.modules.system.service.SysDepartService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.analysis.function.Exp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "excel")
public class RelicExcelController {


    @Autowired
    private GwRelicServiceMapper relicServiceMapper;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private YearsMapper yearsMapper;

    @Autowired
    private SysDepartService departService;


    @SneakyThrows
    @GetMapping(value = "exportExcel")
    public void exportExcel(@RequestParam("ids")List<String> ids, @RequestParam("choseType") List<String> choseType, HttpServletResponse response){

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        String fileName = "ss" + "imageWrite" + System.currentTimeMillis() + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename="+fileName);

        List<GwRelic> gwRelics = relicServiceMapper.selectBatchIds(ids);

        gwRelics.forEach(item ->{

            if (item.getDiffStatus() !=null){
                String diffStatusName=item.getDiffStatus() == 1 ? "二维" : "三维";
                item.setDiffStatusName(diffStatusName);
            }

            if (item.getPql() !=null){
                String pqlName = item.getPql().equals("1") ? "精美" : "普通";
                item.setPql(pqlName);
            }

            //馆藏单位名称
            if (StringUtils.isNotEmpty(item.getTypeId())){
                CollectionEntity entity = (CollectionEntity) collectionService.getById(item.getTypeId());
                if (entity !=null) {
                    item.setCollectionName(entity.getName());
                }
            }

            // 设置部门名称
            String depName = departService.getNameById(item.getDepId());
            item.setDepName(depName);

            //年代
            Years years=yearsMapper.selectById(item.getYearsId());
            if (years !=null){
                item.setYearName(years.getName());
            }

            String cateName = CateNameEnum.getName(item.getCategoryId());
            item.setCategoryName(cateName);
        });

        //需要导出的数据
        List<RelicExcelSubsetEntity> subsetEntities = ExportExcelUtil.excelExport(gwRelics, choseType);

        //主表头
        List<List<String>> head = ExportExcelUtil.head(choseType);

        //用户指定需要导出的数据
        Set<String> includeColumnFiledNames = RelicExcelEnum.includeColumnFiledNames(choseType);


        // 写入数据
        EasyExcel.write(response.getOutputStream(),RelicExcelSubsetEntity.class).includeColumnFiledNames(includeColumnFiledNames).sheet("主标题").head(head).doWrite(subsetEntities);
    }

}
