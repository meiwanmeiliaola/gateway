package com.msm.admin.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 消息实体
 * @author quavario@gmail.com
 * @date 2020/2/12 16:08
 */
@Data
@TableName("msm_msg")
public class Message {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String title;
    private String content;
    private String sender;
    private String receiver;
    private Date sendDate;
    /**
     * 消息状态
     * 1. 已发送
     * 2. 已阅读
     */
    private Integer status;

}
