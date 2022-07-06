package com.msm.admin.modules.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msm.admin.modules.common.entity.TreeData;
import com.msm.admin.modules.common.entity.Message;
import com.msm.admin.modules.common.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Description: 文物
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/messages")
public class MessageController {
	@Autowired
	private MessageService messageService;

    /**
     * 分页列表查询
     */
    @GetMapping
    public IPage list(Page<Message> page, Message message) {
		return messageService.page(page);
	}


	/**
	  *   添加/编辑
	 * @param message
	 * @return
	 */
	@PutMapping
    public Message save(@RequestBody Message message) {
		messageService.saveOrUpdate(message);
		return message;
    }

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
		Message message = messageService.getById(id);
		messageService.removeById(id);
    }

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    public Message queryById(@PathVariable String id) {
		Message message = messageService.getById(id);
		return message;
    }


}
