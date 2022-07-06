package com.msm.admin.greatWall.gwCulture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.msm.admin.framework.handler.StringToListTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author zxy
 * @date 2022/2/11 17:24
 */
@Data
@TableName("gw_culture")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GwCulture extends Model<GwCulture> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String title;

    //缩略图
    private String thumb;

    //移动端缩略图
    @TableField("min_thumb")
    private String minThumb;

    @TableField("zip_url")
    private String zipUrl;

    @TableField("category_id")
    private String categoryId;

    @TableField(exist = false)
    private String categoryName;

    @TableField("create_date")
    private String createDate;

    @TableField("update_date")
    private String updateDate;

    //0:未审核 1:审核通过 2:审核失败
    private Integer status;

    //pc端缩略图
    @TableField("max_thumb")
    private String maxThumb;

    //采集地点
    private String locality;

    //材质规格
    @TableField("SPEC")
    private String spec;

    //形制纹饰
    @TableField("shape_grain")
    private String shapeGrain;

    //书法规格
    private String calligraphy;

    //篆刻时间
    @TableField("seal_cutting")
    private String sealCutting;

    //保存时间
    @TableField("save_state")
    private String saveState;

    private String content;

    @TableField("dep_id")
    private String depId;

    //轮播图
    @TableField(typeHandler = StringToListTypeHandler.class)
    private List<String> slideshow;

    //详情图
    @TableField("info_img")
    private String infoImg;

    //详情缩略图
    @TableField("info_thumb")
    private String infoThumb;

    //碑刻分类
    @TableField("stela_class")
    private String stelaClass;

    @TableField(exist = false)
    private String stelaName;

    @TableField(exist = false)
    private String pcThumb;

    @TableField(exist = false)
    private String moveThumb;

    //姓名图
    @TableField("name_img")
    private String nameImg;

    //焦点图
    @TableField("focus_img")
    private String focusImg;

    //碑刻排序
    private String sort;

    public static GwCulture getCulture(GwCulture culture){
        if (StringUtils.isNotEmpty(culture.getCategoryId()) && "4".equals(culture.getCategoryId())){
            if (StringUtils.isEmpty(culture.getLocality())){
                culture.setLocality("");
            }
            if (StringUtils.isEmpty(culture.getSpec())){
                culture.setSpec("");
            }
            if (StringUtils.isEmpty(culture.getShapeGrain())){
                culture.setShapeGrain("");
            }
            if (StringUtils.isEmpty(culture.getSealCutting())){
                culture.setSealCutting("");
            }
            if (StringUtils.isEmpty(culture.getSaveState())){
                culture.setSaveState("");
            }
            if (StringUtils.isEmpty(culture.getCalligraphy())){
                culture.setCalligraphy("");
            }
            if (StringUtils.isEmpty(culture.getInfoThumb())){
                culture.setInfoThumb("");
            }
            if(StringUtils.isEmpty(culture.getContent())){
                culture.setContent("");
            }
        }
        return culture;
    }
}
