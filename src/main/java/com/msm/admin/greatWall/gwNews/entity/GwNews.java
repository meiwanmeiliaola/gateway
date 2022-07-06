package com.msm.admin.greatWall.gwNews.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zxy
 * @date 2022/2/11 17:03
 */
@Data
@TableName("gw_news_dyn")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GwNews extends Model<GwNews> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String name;

    @TableField("create_date")
    private String createDate;

    private String content;

    @TableField("pc_avatar")
    private String pcAvatar;

    @TableField("move_avatar")
    private String moveAvatar;

    //0:未审核 1:审核通过 2:审核失败
    private Integer status;


}
