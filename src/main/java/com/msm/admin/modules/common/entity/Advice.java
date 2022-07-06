package com.msm.admin.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 *
 * @author quavario@gmail.com
 * @date 2020/12/31 16:46
 */

@Data
@TableName("msm_advice")
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Advice {
    /**id*/
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String title;

    private String opinion;

    private Date sendDate;

    private String sender;

    private String receiver;

    // 审核对象 如文章id 文物ID
    private String itemId;

    private Integer status;
}
