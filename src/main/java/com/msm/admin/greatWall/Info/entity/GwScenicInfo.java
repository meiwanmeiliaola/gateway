package com.msm.admin.greatWall.Info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * ????(GwScenicInfo)实体类
 *
 * @author makejava
 * @since 2022-02-11 15:39:02
 */
@Data
@TableName("gw_scenic_info")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GwScenicInfo extends Model<GwScenicInfo> {

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
     * 创建时间
     */
    @TableField("create_date")
    private String createDate;

    /**
     * 内容
     */
    private String content;

    /**
     * 0:未审核 1:审核通过 2:审核失败
     */
    private Integer status;

    @TableField("pc_thumb")
    private String pcThumb;

    @TableField("move_thumb")
    private String moveThumb;

    //1:新闻动态 2:景区资讯
    @TableField("cate_id")
    private Integer cateId;

    @TableField(exist = false)
    private String cateName;

    private String depId;

    @TableField(exist = false)
    private String depName;

    //简介
    private String intro;

    //
    @TableField("date_range")
    private String dateRange;



}

