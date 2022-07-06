package com.msm.admin.greatWall.gwRelic.entity;

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

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * @author makejava
 * @since 2022-02-09 16:17:21
 * 二维文物表
 */
@Data
@TableName("gw_relic")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GwRelic extends Model<GwRelic> {

    //主键
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    //名称
    @NotBlank(message = "名称不可为空")
    private String name;

    //年代id
    @NotBlank(message = "年代不可为空")
    @TableField("years_id")
    private String yearsId;

    @TableField(exist = false)
    private String yearName;

    //材质id
    @NotBlank(message = "材质不可为空")
    @TableField("mat_id")
    private String materialId;

    @TableField(exist = false)
    private String materialName;


    //部门id
    @NotBlank(message = "部门id不可为空")
    @TableField("dep_id")
    private String depId;

    @TableField(exist = false)
    private String depName;

    //描述
    private String content;

    //轮播图
    @TableField(typeHandler = StringToListTypeHandler.class)
    private List<String> images;

    //缩略图
    private String thumb;

    //1:碑刻 2:兵器 3:建筑构件 4:生产工具 5:生活用具 6:其他
    @TableField("category_id")
    private String categoryId;

    @TableField(exist = false)
    private String categoryName;

    //工艺评定等级(1:精美 2:普通)
    private String pql;

    //创建时间
    @TableField("create_date")
    private String createDate;

    //修改时间
    @TableField("update_date")
    private String updateDate;

    //0:未审核 1:审核通过 2:审核失败
    private Integer status;

    //压缩包
    @TableField("zip_url")
    private String zipUrl;

    //1:二维 2:三维
    @TableField("diff_status")
    private Integer diffStatus;

    @TableField(exist = false)
    private String diffStatusName;

    @TableField(exist = false)
    private String pqlName;

    //馆藏单位
    @TableField(value = "type_id")
    private String typeId;

    @TableField(exist = false)
    private String collectionName;

    //1:存在 2：不存在
    @TableField(exist = false)
    private Integer existsZipUrl;


    @TableField("cate_name")
    private String cateName;



}



