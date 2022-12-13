package com.msm.admin.greatWall.gwRoam;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.data.WriteCellData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import static com.alibaba.excel.enums.BooleanEnum.TRUE;

@Getter
@Setter
@EqualsAndHashCode
@ContentRowHeight(100)
@ColumnWidth(100 / 8)
public class ImageDemoData {

    @ExcelProperty({"主标题","文件"})
    private File file;

    @ExcelProperty({"主标题","文件流"})
    private InputStream inputStream;
    /**
     * 如果string类型 必须指定转换器，string默认转换成string
     */
    @ExcelProperty({"主标题","图片"})
    private String string;

    @ExcelProperty({"主标题","byte类型"})
    private byte[] byteArray;
    /**
     * 根据url导出
     *
     * @since 2.1.1
     */
    @ExcelProperty({"主标题","路径"})
    private URL url;

    /**
     * 根据文件导出 并设置导出的位置。
     *
     * @since 3.0.0-beta1
     */
/*    @ContentFontStyle()
    private WriteCellData<Void> writeCellDataFile;*/
}
