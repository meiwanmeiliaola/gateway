package com.msm.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msm.admin.modules.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author quavario@gmail.com
 * @date 2019/8/2 15:54
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    int insertBatchWithUserId(@Param("rolesId") List<String> rolesId, @Param("userId") String userId);
}
