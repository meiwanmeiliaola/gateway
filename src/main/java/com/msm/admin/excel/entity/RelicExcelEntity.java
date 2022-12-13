package com.msm.admin.excel.entity;


import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.net.URL;

@Getter
@Setter
@EqualsAndHashCode
@ContentRowHeight(100)
@ColumnWidth(100 / 8)
public class RelicExcelEntity {


    //名称
    @ColumnWidth(40)
    private String name;

    //年代
    private String yearName;

    //材质
    private String materialName;

    //部门
    private String depName;

    //图片
    private URL thumb;

    //1:碑刻 2:兵器 3:建筑构件 4:生产工具 5:生活用具 6:其他
    private String categoryName;

    //工艺评定等级(1:精美 2:普通)
    private String pql;


}
