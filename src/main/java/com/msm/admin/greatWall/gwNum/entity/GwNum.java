package com.msm.admin.greatWall.gwNum.entity;

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

import java.io.Serializable;
import java.util.List;

/**
 * ????(GwNum)表实体类
 *
 * @author makejava
 * @since 2022-02-11 14:56:54
 */
@SuppressWarnings("serial")
@Data
@TableName("gw_num")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GwNum extends Model<GwNum> {

    //主键
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    //名称
    private String title;

    //排序
    private String sort;

    //图片
    @TableField(typeHandler = StringToListTypeHandler.class)
    private List<String> thumb;

    //描述
    private String content;

    //电话
    private String mobile;

    //开放时间
    @TableField("open_time")
    private String openTime;

    //地址
    private String address;

    //公交路线
    private String busRoute;

    //所属区域(1:秦皇岛 2:唐山 3:承德 4:张家口  5:保定)
    private String area;

    //创建时间
    @TableField("create_date")
    private String createDate;

    //修改时间
    @TableField("update_date")
    private String updateDate;

    //0:未审核 1:审核通过 2:审核失败
    private Integer status;

    //全景漫游
    private String pano;

    @TableField(exist = false)
    private String areaName;

    @TableField("dep_id")
    private String depId;



    }

