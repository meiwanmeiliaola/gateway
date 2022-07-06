package com.msm.admin.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msm.admin.modules.system.entity.SysUser;

import java.util.Map;

/**
 * @Description: 用户
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
public interface SysUserService extends IService<SysUser>{

    /**
     * @param username 登录名loginName
     * @return 用户
     */
    SysUser getByUsername(String username);

    IPage pageSearch(Page<SysUser> page, SysUser sysUser);

    void delAvatar(String userId);

    /**
     * @param name 昵称 name
     * @return 用户
     */
    SysUser getByName(String name);
    Map userInfo();

    void resetPw(String pw, String newPw, String comfirmPw);

    String getNameById(String id);

    String freezeUser(String id);

    String unFreezeUser(String id);
}
