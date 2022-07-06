package com.msm.admin.greatWall.gwCollection.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/3/15 15:00
 */
@Data
@TableName("msm_collection")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectionEntity {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String name;

    @TableField("area_id")
    private String areaId;

    @TableField("create_date")
    private String createDate;

    //1:文物收藏 2:全景漫游
    private String type;

}
