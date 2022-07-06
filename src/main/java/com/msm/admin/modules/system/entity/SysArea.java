package com.msm.admin.modules.system.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.msm.admin.modules.common.entity.TreeData;
import lombok.Data;
import lombok.ToString;

//import com.qdkj.msm.domain.v2.SysUser;

/**
 * @Description: area
 * @Author: quavario
 * @Date: 2019-05-30
 * @Version: V1.0
 */
@Data
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysArea extends TreeData<SysArea> {
    /**
     * 地区名
     */
    private String name;

    /**
     * sort
     */
    private int sort;

    /**
     * type
     */
    private int type;


}
