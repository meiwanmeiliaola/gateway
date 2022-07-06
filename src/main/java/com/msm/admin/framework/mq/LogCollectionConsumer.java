package com.msm.admin.framework.mq;

import com.msm.admin.common.constant.RabbitRoutingQueue;
import com.msm.admin.modules.monitor.entity.SysLog;
import com.msm.admin.modules.monitor.service.SysLogService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 异常信息处理
 * @author quavario@gmail.com
 * @date 2020/4/9 9:56
 */
@Component
public class LogCollectionConsumer {


    @Autowired
    SysLogService logService;

    /**
     * @param log 异常信息
     */
    // TODO
    @RabbitListener(queues = {RabbitRoutingQueue.LOG_QUEUE})
    public void exceptionCollect(SysLog log) {
        System.out.println(log);
//        logService.save(log);
    }
}
