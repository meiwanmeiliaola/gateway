package com.msm.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.msm.admin.modules.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author quavario@gmail.com
 * @date 2019/12/18 17:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysDictValue extends BaseEntity {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /** 字典类型ID */
    @TableField("dict_type_id")
    private String dictTypeId;
    /** 字典编码 */
//    private Long code;

    /** 字典排序 */
    private Integer sort;

    /** 字典标签 */
    private String label;

    /** 字典键值 */
    private String value;

    // 状态
    private Integer status;

    /** 描述 */
    private String remark;

    //目前用在碑刻分类功能上,pc端缩略图
    @TableField("pc_thumb")
    private String pcThumb;

    //目前用在碑刻分类功能上,移动端图片
    @TableField("move_thumb")
    private String moveThumb;

    //目前用在碑刻分类功能上,选中的缩略图
    @TableField("pitch_on_thumb")
    private String pitchOnThumb;

    //目前用在碑刻分类功能上,未选中的缩略图
    @TableField("un_checked_thumb")
    private String unCheckedThumb;

}
