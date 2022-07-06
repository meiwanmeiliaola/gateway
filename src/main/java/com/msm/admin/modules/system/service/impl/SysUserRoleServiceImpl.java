package com.msm.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.modules.system.entity.SysUserRole;
import com.msm.admin.modules.system.mapper.SysUserRoleMapper;
import com.msm.admin.modules.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色service
 * @author quavario@gmail.com
 * @date 2019/9/6 14:05
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 批量新增userRole
     * TODO 数据库设置了级联删除，删除user后，关联的userRole同时会被删除
     * @param rolesId 角色ID列表
     * @param userId 用户id
     * @return 增加数量
     */
    @Override
    public int addBatchWithUserId(List<String> rolesId, String userId) {
        // 判空
        if (CollectionUtils.isEmpty(rolesId)) {
            return 0;
        }

        // 去重
        rolesId = rolesId.stream().distinct().collect(Collectors.toList());
        Assert.notEmpty(rolesId, "rolesId为空");
        return sysUserRoleMapper.insertBatchWithUserId(rolesId, userId);
    }

    @Override
    public int removeByUserId(String userId) {
        return sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaUpdate().eq(SysUserRole::getUserId, userId));
    }

    /**
     * 批量更新userRole
     * @param rolesId 角色ID列表
     * @param userId 用户id
     * @return 增加数量
     */
    @Override
    public int updateBatchWithUserId(List<String> rolesId, String userId) {
        // 判空
        if (CollectionUtils.isEmpty(rolesId)) {
            return 0;
        }
        removeByUserId(userId);
        return addBatchWithUserId(rolesId, userId);
    }

}
