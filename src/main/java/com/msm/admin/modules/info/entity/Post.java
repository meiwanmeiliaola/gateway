package com.msm.admin.modules.info.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.msm.admin.modules.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @Description: 文章
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Data
@TableName("msm_post")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post extends BaseEntity {
    
	/**id*/
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**文章标题*/
	@NotBlank(message = "标题不能未空")
	private String title;


	/**子标题*/
	private String subTitle;

//	@(min = 2010, max = 2050, message = "超过最大日期")
	@Min(2010)
	@Max(value = 2050, message = "超过最大日期")
	private Integer holdingDate;

	/**文章内容*/
	private String content;


	/** 按单位查询 */
	@TableField(fill = FieldFill.INSERT)
	private String depId;

	/** 博物馆名称 */
	@TableField(exist = false)
	private String depName;

	/**封面图片*/
	private String thumb;

	/**点击次数*/
	private Integer hitCount;

	/**
	 * 文章所属分类
	 * 1: 文博新闻
	 * 3: 重大活动
	 * 4: 博物馆展览的关联id 无用
	 * 6: 文物课堂
	 * 7: 学术研究
	 */
	@NotNull(message = "类型不能为空")
	private Integer type;

	// 1 审核通过 首页显示 2. 审核不通过  3.已发布 待审核 4 草稿
	@NotNull(message = "状态不能未空")
	private Integer status;

}
