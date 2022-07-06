package com.msm.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.msm.admin.modules.common.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description: 用户
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Data
public class SysUser extends BaseEntity {
    
	/**id*/
	@TableId(type = IdType.UUID)
	private String id;

	private String depId;

	/**loginName*/
	@NotBlank(message = "用户名不能未空")
	private String loginName;

	/**password*/
	@TableField(select = false)
	private String password;

	/**name*/
	@NotBlank(message = "昵称不能未空")
	private String name;

	/**email*/
	@Email
	private String email;

	/**phone*/
	private String phone;

	/**photo*/
	private String avatar;

	/**loginIp*/
	private String loginIp;

	/**loginDate*/
	private Date loginDate;

	@TableField(exist = false)
	private List<String> roles;

	/** 1 审核通过 首页显示 2. 审核不通过  3.待审核 4 草稿  */
	/** 1正常 2冻结 */
	private Integer status;

//	private Boolean admin;
	/**
	 * 1：管理员 2: 用户
	 */
	private Integer type;

	public Boolean admin() {
		return type == 1;
	}

}
