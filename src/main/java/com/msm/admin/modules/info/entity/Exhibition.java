package com.msm.admin.modules.info.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.msm.admin.modules.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description: 展览
 * @Author: quavario
 * @Date:   2019-06-13
 * @Version: V1.0
 */
@Data
@TableName(value = "msm_exhibition")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exhibition extends BaseEntity {
    
	/**主键*/
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**文章id*/
//	private String postId;

	@NotBlank(message = "名称不能未空")
	private String title;

	/** 全景地址 */
	private String pano;

	/** 全景缩略图 */
	private String panoThumb;

	/** 全景介绍图 */
	private String panoIntroImage;

	private String content;

	private String thumb;

	private Date holdingDate;

	/**展览开始时间*/
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	/**展览结束时间*/
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	private Date closeTime;

	/**开放时间*/
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	private String openTime;

	/**展览类型1:常设展览，2：临展与交流展*/
	@NotNull(message = "类型不能为空")
	private Integer type;

	@TableField(fill = FieldFill.INSERT)
	private String depId;

	@TableField(exist = false)
	private String areaId;

	// 1 审核通过 首页显示 2. 审核不通过  3.待审核 4 草稿
	private Integer status;
}
