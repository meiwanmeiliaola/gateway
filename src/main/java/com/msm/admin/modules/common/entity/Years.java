package com.msm.admin.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author quavario@gmail.com
 * @date 2019/12/20 10:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("msm_years")
public class Years extends TreeData<Years> {
    /**
     * 名称
     * */
    private String name;

    /**
     * 缩略图
     */
    private String image;

    /**
     * banner 图片
     */
    private String banner;

    /** 简介 */
    private String content;

    /** 图标 */
    private String icon;

    /** 类型 */
    private Integer type;

    /** 排序 */
    private Integer sort;


    /** 状态 */
    private Integer status;

    /** 备注 */
    private String remarks;
}
