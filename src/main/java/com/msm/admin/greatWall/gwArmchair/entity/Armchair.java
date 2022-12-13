package com.msm.admin.greatWall.gwArmchair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("gw_armchair")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Armchair {


    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    //缩略图
    private String thumb;

    //标题浮动缩略图
    @TableField("title_image1")
    private String titleImage1;

    //标题静态缩略图
    @TableField("title_image2")
    private String titleImage2;

    //内容
    private String content;

    //内容缩略图
    @TableField("content_image")
    private String contentImage;

    //三维模型
    @TableField("three_url")
    private String threeUrl;

    //创建时间
    @TableField("create_date")
    private String createDate;

    //修改时间
    @TableField("update_date")
    private String updateDate;

    //0:未审核 1:审核通过 2:审核失败
    private Integer status;


}
