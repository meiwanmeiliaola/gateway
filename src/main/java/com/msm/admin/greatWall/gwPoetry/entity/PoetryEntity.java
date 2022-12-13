package com.msm.admin.greatWall.gwPoetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.msm.admin.greatWall.gwNum.entity.GwNum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("gw_poetry")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PoetryEntity extends Model<PoetryEntity> {

    //主键
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    //标题缩略图
    @TableField("title_img")
    private String titleImg;

    //文献来源
    private String source;

    //音频缩略图
    private String images;

    //内容
    private String content;

    //音频
    private String audio;

    /**
     * 0:未审核 1:审核通过 2:审核失败
     */
    private Integer status;

    //创建时间
    @TableField("create_date")
    private String createDate;

    //修改时间
    @TableField("update_date")
    private String updateDate;




}
