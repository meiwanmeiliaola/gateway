package com.msm.admin.framework.core;

import com.msm.admin.config.RabbitConfig;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.msm.admin.common.constant.RabbitRoutingQueue.EXCEPTION_ROUTING;
import static com.msm.admin.common.constant.RabbitRoutingQueue.LOG_ROUTING;

/**
 * @author quavario@gmail.com
 * @date 2020/4/9 10:59
 */
@Component
public class RabbitSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @AllArgsConstructor
    public class Sender {
        private String routing;
        public void send(Object message) {
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, this.routing, message);
        }
    }

    public Sender routeException() {
        return new Sender(EXCEPTION_ROUTING);
    }

    public Sender routeLog() {
        return new Sender(LOG_ROUTING);

    }

    public Sender custom(String routing) {
        return new Sender(routing);
    }

}
