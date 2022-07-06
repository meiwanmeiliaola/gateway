package com.msm.admin.modules.common.service.impl;

import com.msm.admin.modules.common.entity.ResultMessage;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * @author quavario@gmail.com
 * @date 2020/1/3 11:27
 */
@Service
public class ResultService {
    private static Map<String, ResultMessage> msgMap;

    void put(String message, Integer status) {
        String key = UUID.randomUUID().toString();
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setMessage(message);
        resultMessage.setStatus(status);
        msgMap.put(key, resultMessage);
    }
}
