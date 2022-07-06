
package com.msm.admin.framework.mq;

import com.msm.admin.common.constant.RabbitRoutingQueue;
import com.msm.admin.modules.monitor.entity.SysException;
import com.msm.admin.modules.monitor.service.SysExceptionService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * 异常信息处理
 * @author quavario@gmail.com
 * @date 2020/4/9 9:56
 */

@Component
public class ExceptionCollectionConsumer {


    @Autowired
    SysExceptionService exceptionService;

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String adminEmail;

    @AllArgsConstructor
    class SendExceptionNoticeEmail implements Runnable {
        SysException exception;

        @Override
        public void run() {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(adminEmail);
            mailMessage.setTo("quav@qq.com");
            mailMessage.setSubject("系统出现异常-" + new Date().toLocaleString());
            mailMessage.setText(exception.getMessage());
            javaMailSender.send(mailMessage);
        }
    }



/**
     * @param message 异常信息
     */

    @RabbitListener(queues = {RabbitRoutingQueue.EXCEPTION_QUEUE})
    public void exceptionCollect(SysException message) {
        // 创建线程发送邮件
        SendExceptionNoticeEmail sendExceptionNoticeEmail = new SendExceptionNoticeEmail(message);
        Thread thread = new Thread(sendExceptionNoticeEmail);
        thread.start();

        // TODO
//        exceptionService.save(message);
    }


}

