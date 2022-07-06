package com.msm.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.msm.admin.modules.common.entity.TreeData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description: 单位
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysDepart extends TreeData<SysDepart> {


	/**单位名称*/
	@NotBlank(message = "名称不能未空")
	private String name;

	/**收藏数量*/
	private Integer collCount;

	/**单位图片*/
	private String thumb;

	/**排序*/
	private Integer sort;

	/**单位所属区域ID*/
    private String areaId;

	/** 全景链接 */
	private String pano;

	/** 全景缩略图 */
	private String panoThumb;

	/** 单位描述 */
	private String content;

	private String phone;

	private String address;

	/** 单位类型  1 历史博物馆 2 革命展馆 */
	private Integer type;

	private String route;

	private String openTime;


	// 1 审核通过 首页显示 2. 审核不通过  3.待审核 4 草稿
	@NotNull(message = "状态不能未空")
	private Integer status;

}
