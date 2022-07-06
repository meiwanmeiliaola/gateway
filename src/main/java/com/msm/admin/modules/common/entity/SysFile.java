package com.msm.admin.modules.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author quavario@gmail.com
 * @date 2019/4/18 14:38
 */
@Data
public class SysFile extends BaseEntity {
    /**id*/
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;         //id
    private String oriName;     //原名称
    private String uniName;     //唯一名称
    private Long size;          //大小
    private String localPath;      //本地绝对路径
    private String urlPath;  //url相对路径
    private Integer type;

}
