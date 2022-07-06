package com.msm.admin.greatWall.gwExp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zxy
 * @date 2022/3/6 11:46
 */
@Data
@TableName("gw_exp")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exp {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String title;

    @TableField("pc_thumb")
    private String pcThumb;

    @TableField("move_thumb")
    private String moveThumb;

    @TableField("create_date")
    private String createDate;

    @TableField("update_date")
    private String updateDate;

    @TableField("zip_url")
    private String zipUrl;

}
