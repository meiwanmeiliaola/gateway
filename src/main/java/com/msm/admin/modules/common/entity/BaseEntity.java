package com.msm.admin.modules.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author quavario@gmail.com
 * @date 2019/12/11 15:54
 */
@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {
    /**
     * 获取createBy.id的值根据驼峰命名转下划线映射到create_by字段
     * */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**创建时间*/
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**更新者*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;
}
