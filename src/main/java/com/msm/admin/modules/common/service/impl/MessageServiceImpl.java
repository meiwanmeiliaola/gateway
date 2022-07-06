package com.msm.admin.modules.common.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.common.entity.Message;
import com.msm.admin.modules.common.mapper.MessageMapper;
import com.msm.admin.modules.common.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: area
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}
