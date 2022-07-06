package com.msm.admin.common.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * rabbit路由队列绑定
 * @author quavario@gmail.com
 * @date 2020/4/9 10:30
 */
public class RabbitRoutingQueue {
    public static final String EXCEPTION_ROUTING = "routing.msm.exception";
    public static final String EXCEPTION_QUEUE = "queue.msm.exception.collection";

    public static final String STAT_VIEW_ROUTING = "routing.msm.statView";
    public static final String STAT_VIEW_QUEUE = "queue.msm.statView.collection";

    public static final String LOG_ROUTING = "routing.msm.log";
    public static final String LOG_QUEUE = "queue.msm.log.collection";

    public static final String EMAIL_ROUTING = "routing.msm.email";
    public static final String EMAIL_QUEUE = "queue.msm.email";
}
