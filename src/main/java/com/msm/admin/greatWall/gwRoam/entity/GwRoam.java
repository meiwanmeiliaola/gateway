package com.msm.admin.greatWall.gwRoam.entity;

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
 * @date 2022/2/18 9:57
 */
@Data
@TableName("gw_pan_roam")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GwRoam extends Model<GwRoam> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("depart_id")
    private String departId;

    @TableField("pano")
    private String pano;

    @TableField("create_date")
    private String createDate;

    @TableField("update_date")
    private String updateDate;

    //0:未审核 1:审核通过 2:审核失败
    private Integer status;

}
