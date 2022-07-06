package com.msm.admin.modules.system.entity;

import com.msm.admin.modules.common.entity.TreeData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description: menu
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends TreeData {

	/**name*/
	@NotBlank(message = "名称不能未空")
	private String text;

	/**sort*/
	private Integer sort;

	/**
	 * 路由
	 */
//	@NotBlank(message = "路由不能为空")
	private String link;


	/**
	 * 权限
	 */
//	@NotBlank(message = "权限不能为空")
	private String perms;

	/**
	 * 图标
	 */
	private String icon;

	private Integer status;

	/**
	 * 类型 1:目录 2：菜单 3：按钮
	 */
	@NotNull(message = "类型不能为空")
	private int type;
}
