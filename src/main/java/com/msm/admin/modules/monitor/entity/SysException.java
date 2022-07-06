package com.msm.admin.modules.monitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.msm.admin.modules.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author quavario@gmail.com
 * @date 2020/1/8 9:31
 */
@Data
@TableName("sys_exception")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysException {
    /**id*/
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /** 异常消息 */
    private String message;

    /** 用户ID */
    private String userId;


    /** 用户名称 */
    private String username;

    /** 操作ip */
    private String ip;

    /** 请求url */
    private String requestUrl;

    /** 请求参数 */
    private String requestParam;

    /** 请求方式 */
    private String requestType;

    private Date recordDate;

}
