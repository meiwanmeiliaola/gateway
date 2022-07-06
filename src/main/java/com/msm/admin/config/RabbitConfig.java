package com.msm.admin.config;

import com.msm.admin.common.constant.RabbitRoutingQueue;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.msm.admin.common.constant.RabbitRoutingQueue.*;

/**
 * @author quavario@gmail.com
 * @date 2020/3/7 21:28
 */
@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "ex.msm.default";

    /**
     * @return 交换机
     */
    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(EXCHANGE).durable(true).build();
    }

    /**
     * @return 队列
     */
    @Bean(EXCEPTION_QUEUE)
    public Queue exceptionQueue() {
        return QueueBuilder.durable(EXCEPTION_QUEUE).build();
    }

    @Bean(LOG_QUEUE)
    public Queue logQueue() {
        return QueueBuilder.durable(LOG_QUEUE).build();
    }


    /**
     * @param exchange 交换机
     * @param queue 队列
     * @return 绑定结果
     */
    @Bean
    public Binding bindingException(@Autowired Exchange exchange, @Qualifier(EXCEPTION_QUEUE) Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(EXCEPTION_ROUTING).noargs();
    }

    @Bean
    public Binding bindingLog(@Autowired Exchange exchange, @Qualifier(LOG_QUEUE) Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(LOG_ROUTING).noargs();
    }


    /**
     * 消息对象序列化
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * 接收消息对象反序列化
     */
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 指定反序列化方式为jackson
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

}
