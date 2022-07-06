package com.msm.admin.modules.analysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 浏览量统计
 * @author quavario@gmail.com
 * @date 2020/1/15 17:56
 */
@Data
@TableName("msm_stat_view")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatView {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 访问项ID
     */
    private String itemId;
    /**
     * 访问时间
     */
    private Date recordDate;

    /**
     * 访问ip
     * TODO NGINX代理设置转发header中的原始IP，否则只能记录本机IP
     */
    private String visitorIp;

    /**
     * user agent原始字符串
     */
    private String userAgent;
    /**
     * 访问终端
     * @see eu.bitwalker.useragentutils.DeviceType
     */
    private String device;

    /**
     * 浏览器
     * @see eu.bitwalker.useragentutils.Browser
     */
    private String browser;

    /**
     * 操作系统
     * @see eu.bitwalker.useragentutils.OperatingSystem
     */
    private String os;

    /**
     * 类型
     * 1: 二维文物
     * relic-common
     */
    private String type;
}
