package com.msm.admin.modules.relic.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.msm.admin.framework.handler.StringToListTypeHandler;
import com.msm.admin.modules.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description: 文物
 * @Author: quavario
 * @Date:   2019-06-13
 * @Version: V1.0
 */
@Data
@TableName("msm_relic")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Relic extends BaseEntity {
    
	/**id*/
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * 文物名称
	 * */
	@NotBlank(message = "名称不能未空")
	private String name;
	/**
	 * 文物图片
	 * */
	@TableField(typeHandler = StringToListTypeHandler.class)
	private List<String> images;

	/**
	 * 缩略图
	 * */
	@NotBlank(message = "缩略图不能为空")
	private String thumb;

	/**
	 * 描述
	 * */
	private String content;

	@TableField("url_3d")
	private String url3d;

	/** 全景 */
	private String pano;

	/**
	 * 移除
	 */
	@Deprecated
	private String url;

	/**
	 * 收藏单位
	 * */
	@TableField(fill = FieldFill.INSERT)
	private String depId;

	@TableField(exist = false)
	private String depName;

	/**
	 * 文物类别ID
	 */
	private String categoryId;

	@TableField(exist = false)
	private String categoryName;

	/**
	 * 文物年代ID
	 */
	private String yearsId;


	/** 数量 */
	private Integer count;

	// 排序
	private Integer sort;

	/** 文物级别 */
	private String levelId;

	/** 文物来源 */
	private String sourceId;

	/** 完残程度 */
	private String integrityId;

	/** 收藏日期 */
	@TableField("coll_date_id")
	private String collectionDateId;

	/** 保存状态ID */
	@TableField("pres_state_id")
	private String preservationStateId;

	/** 材质ID */
	@TableField("mat_id")
	private String materialId;

	/** 所在地区 */
	private String areaId;

	/** 重量 */
	private Float weight;
	// private String detailId;

	// 1 审核通过 首页显示 2. 审核不通过  3.待审核 4 草稿
	@NotNull(message = "状态不能未空")
	private Integer status;

	/** 革命文物年代
	 * 1.  1840——1919年     旧民主主义革命时期
	 * 2.  1919——1927年     国民革命时期
	 * 3.  1927——1937年     土地革命时期
	 * 4.  1937——1945年     抗日战争时期
	 * 5.  1945——1949年     解放战争时期
	 * 6. 近现代（凡是革命文物没有确切年代划入此时期）
 	 */
	private String revRelicYears;

	/**
	 * 1：河北文物 2：典藏精品 3：三维文物  4: 革命文物-普通文物 5： 革命文物-全景文物 6：革命文物-三维文物
	 * */
	@NotNull(message = "类型不能为空")
	private Integer type;
//	@TableField(typeHandler = com.msm.admin.framework.handler.RelicTypeHandler.class)
//	private RelicTypeEnum type;
}
