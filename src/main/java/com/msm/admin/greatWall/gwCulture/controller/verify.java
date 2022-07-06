package com.msm.admin.greatWall.gwCulture.controller;

import com.msm.admin.utils.ImageVerificationCode;
import com.msm.admin.utils.SCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping(value = "demo")
public class verify {




    @GetMapping(value = "image")
    public void verification(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //实例生成验证码对象
        SCaptcha instance = new SCaptcha();
        //将验证码存入session
        session.setAttribute("verification", instance.getCode());
        //向页面输出验证码图片
        instance.write(response.getOutputStream());
    }
}
