package com.msm.admin.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @Description: 文物探秘
 * @Author: quavario
 * @Date:   2019-06-20
 * @Version: V1.0
 */
@Data
@TableName("msm_explore")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Explore extends BaseEntity {
	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * title
	 */
	@NotBlank(message = "标题不能未空")
	private String title;

	/**image*/
	@NotBlank(message = "缩略图不能未空")
	private String thumb;

	/**Url*/
	@NotBlank(message = "链接不能未空")
	private String url;


	private String depId;

	// 1 审核通过 首页显示 2. 审核不通过  3.待审核 4 草稿
	private Integer status;

	/**
	 * 类型
	 * 1：文物解析  2：探秘解密
	 */
	@NotNull(message = "状态不能未空")
	private Integer type;
}
