package com.msm.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.msm.admin.modules.common.entity.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @Description: 角色
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Data
public class SysRole extends BaseEntity {
    
	/**id*/
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/** 名称 */
	private String name;

	/** 编码 */
	private String code;

	@TableField(exist = false)
	private List<String> menus;

	private Integer status;

	/** 备注 */
	private String remarks;
}
