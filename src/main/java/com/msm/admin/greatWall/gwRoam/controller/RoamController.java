package com.msm.admin.greatWall.gwRoam.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.ImageData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.greatWall.gwRoam.ImageDemoData;
import com.msm.admin.greatWall.gwRoam.entity.GwRoam;
import com.msm.admin.greatWall.gwRoam.service.RoamService;
import com.msm.admin.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zxy
 * @date 2022/2/18 9:55
 * 全景漫游
 */
@RestController
@RequestMapping(value = "gwRoam")
public class RoamController {


    @Autowired
    private RoamService roamService;

    @PutMapping
    public GwRoam save(@RequestBody GwRoam gwRoam){
        if(StringUtils.isNotEmpty(gwRoam.getCreateDate())){
            gwRoam.setUpdateDate(DateUtil.getNowTime());
        }else {
            //获取当前时间
            gwRoam.setCreateDate(DateUtil.getNowTime());
        }
        if (gwRoam.getStatus() ==null){
            gwRoam.setStatus(0);
        }
        roamService.saveOrUpdate(gwRoam);
        return gwRoam;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id){
        roamService.removeById(id);
    }

    @GetMapping("/{id}")
    public GwRoam getById(@PathVariable String id){
        GwRoam gwRoam = roamService.getById(id);
        return gwRoam;
    }

    @GetMapping("/list")
    public Page page(Page<GwRoam> page,GwRoam roam){
        return roamService.pageSearch(page,roam);
    }



    @GetMapping("test")
    public void test(HttpServletResponse response, @RequestParam(value = "includeColumnFiledNames") Set<String> includeColumnFiledNames) throws Exception{

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        String fileName = "ss" + "说是" + System.currentTimeMillis() + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename="+fileName);





         String imagePath = "C:\\Users\\qiandian\\Pictures\\Camera Roll\\"+ File.separator + "448800ffd4473c043fdbf8d8397b5e4.jpg";
            try (InputStream inputStream = FileUtils.openInputStream(new File(imagePath))) {
                List<ImageDemoData> list =  ListUtils.newArrayList();
                ImageDemoData imageDemoData = new ImageDemoData();
                list.add(imageDemoData);
                // 放入五种类型的图片 实际使用只要选一种即可
                imageDemoData.setByteArray(FileUtils.readFileToByteArray(new File(imagePath)));
                imageDemoData.setFile(new File(imagePath));
                imageDemoData.setString(imagePath);
                imageDemoData.setInputStream(inputStream);
                imageDemoData.setUrl(new URL(
                        "http://47.92.231.181:9002/data/image/3a24c612-6631-4d88-b27d-f4866a9bbde2.png"));

                // 这里演示
                // 需要额外放入文字
                // 而且需要放入2个图片
                // 第一个图片靠左
                // 第二个靠右 而且要额外的占用他后面的单元格
                //WriteCellData<Void> writeCellData = new WriteCellData<>();
                //imageDemoData.setWriteCellDataFile(writeCellData);
                // 这里可以设置为 EMPTY 则代表不需要其他数据了
                //writeCellData.setType(CellDataTypeEnum.EMPTY);
                //writeCellData.setStringValue("额外的放一些文字");

                // 可以放入多个图片
/*                List<ImageData> imageDataList = new ArrayList<>();
                ImageData imageData = new ImageData();
                imageDataList.add(imageData);
                writeCellData.setImageDataList(imageDataList);
                // 放入2进制图片
                imageData.setImage(FileUtils.readFileToByteArray(new File(imagePath)));
                // 图片类型
                imageData.setImageType(ImageData.ImageType.PICTURE_TYPE_PNG);
                // 上 右 下 左 需要留空
                // 这个类似于 css 的 margin
                // 这里实测 不能设置太大 超过单元格原始大小后 打开会提示修复。暂时未找到很好的解法。
                imageData.setTop(5);
                imageData.setRight(40);
                imageData.setBottom(5);
                imageData.setLeft(5);

                // 放入第二个图片
                imageData = new ImageData();
                imageDataList.add(imageData);
                writeCellData.setImageDataList(imageDataList);
                imageData.setImage(FileUtils.readFileToByteArray(new File(imagePath)));
                imageData.setImageType(ImageData.ImageType.PICTURE_TYPE_PNG);
                imageData.setTop(5);
                imageData.setRight(5);
                imageData.setBottom(5);
                imageData.setLeft(50);
                // 设置图片的位置 假设 现在目标 是 覆盖 当前单元格 和当前单元格右边的单元格
                // 起点相对于当前单元格为0 当然可以不写
                imageData.setRelativeFirstRowIndex(0);
                imageData.setRelativeFirstColumnIndex(0);
                imageData.setRelativeLastRowIndex(0);
                // 前面3个可以不写  下面这个需要写 也就是 结尾 需要相对当前单元格 往右移动一格
                // 也就是说 这个图片会覆盖当前单元格和 后面的那一格
                imageData.setRelativeLastColumnIndex(1);*/


                // 写入数据
                EasyExcel.write(response.getOutputStream(), ImageDemoData.class).includeColumnFieldNames(includeColumnFiledNames).sheet("主标题111").doWrite(list);
        }
    }


}
