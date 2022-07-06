package com.msm.admin.modules.app.entity;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Description: ruins
 * @Author: quavario
 * @Date:   2019-06-20
 * @Version: V1.0
 */
@Data
@TableName("msm_ruins")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ruins extends BaseEntity {
	/**id*/
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**title*/
	@NotBlank(message = "标题不能未空")
	private String title;

	/**subTitle*/
	private String subTitle;

	/**thumb*/
	@NotBlank(message = "缩略图不能未空")
	private String thumb;

	/**url*/
	@NotBlank(message = "内容不能未空")
//	private String url;
	private String content;

	/**ruinsCategoryId*/
	private String categoryId;

	/**ruinsYearsId*/
	private String yearsId;

	/**areaId*/
	private String areaId;

	// 1 审核通过 首页显示 2. 审核不通过  3.待审核 4 草稿
//	@NotNull(message = "状态不能未空")
	private Integer status;
}
