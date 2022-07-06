package com.msm.admin.modules.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.msm.admin.modules.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author quavario@gmail.com
 * @date 2020/1/15 17:51
 */
@Data
//@TableName("mon_pop_search")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PopSearch {
    private String id;
    private String keywords;
    private Date searchDate;
    private Integer type;
}
