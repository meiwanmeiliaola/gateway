package com.msm.admin.modules.info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.msm.admin.modules.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 历史名人
 * @Author: quavario
 * @Date:   2019-06-13
 * @Version: V1.0
 */
@Data
@TableName("msm_celebrity")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Celebrity extends BaseEntity {
    
	/**id*/
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**name*/
	@NotBlank(message = "名称不能未空")
	private String name;

	/**avatar*/
	private String avatar;

	/**age*/
	private String yearsId;

	@TableField(exist = false)
	private String yearsName;

	/**description*/
	private String content;

	/**depId*/
	private String depId;

	private Integer status;
}
