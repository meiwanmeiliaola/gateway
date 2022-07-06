package com.msm.admin.greatWall.overview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.msm.admin.framework.handler.StringToListTypeHandler;
import com.msm.admin.greatWall.gwRelic.entity.GwRelic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * (GwOverview)实体类
 *
 * @author makejava
 * @since 2022-02-11 11:27:42
 */
@Data
@TableName("gw_overview")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GwOverview extends Model<GwOverview> {
    private static final long serialVersionUID = 459762258609206745L;
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 标题
     */
    private String title;

    /**
     * 缩略图
    */
    private String thumb;

    /**
     * 轮播图
    */
    @TableField(typeHandler = StringToListTypeHandler.class)
    private List<String> images;

    /**
     * 语音
    */
    private String voice;

    /**
     * 所属长城距离
     */
    private String distance;
    /**
     * 所属长城起点
     */
    private String origin;
    /**
     * 材质id
     */
    @TableField("material_id")
    private String materialId;

    @TableField(exist = false)
    private String materialName;
    /**
     * 详情介绍
     */
    private String content;
    /**
     * 所属长城终点
     */
    private String dest;
    /**
     * 1:中国长城  2:明长城  3:河北长城 4:战国长城
     */
    private String affiliation;

    @TableField(exist = false)
    private String affiliationName;

    @TableField("create_date")
    private String createDate;

    @TableField("update_date")
    private String updateDate;

    //0:未审核 1:审核通过 2:审核失败
    private Integer status;

    private String pid;

    @TableField("dep_id")
    private String depId;

    @TableField(exist = false)
    private List<GwOverview> gwOverviewList;




}

