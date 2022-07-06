package com.msm.admin.utils;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msm.admin.modules.system.entity.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.ui.Model;

import java.util.Optional;

/**
 * @author quavario@gmail.com
 * @date 2019/12/16 17:49
 */
public class SubjectUtils {
    public static SysUser getCurrentUser() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null) {
            principal = new SysUser();
        }
        return (SysUser) principal;
    }

    public static Boolean isAdmin() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser = (SysUser) principal;
        return sysUser.getType() == 1;
    }

    public static Boolean isAdminNull(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser = (SysUser) principal;
        return sysUser ==null;
    }

    public static QueryWrapper isAdmin(QueryWrapper<?> wrapper){
        //如果不存在账户代表是前端访问
        if (isAdminNull()){
            return wrapper;
        }
        //判断是否是管理员
        if (!isAdmin()){
            SysUser sysUser=getCurrentUser();
            //按照馆藏单位查询
            wrapper.eq("dep_id",sysUser.getDepId());
        }
        return wrapper;

    }



}
