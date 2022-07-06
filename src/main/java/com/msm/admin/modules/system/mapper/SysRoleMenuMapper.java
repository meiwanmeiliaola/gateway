package com.msm.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msm.admin.modules.system.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author quavario@gmail.com
 * @date 2019/8/15 17:45
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    void insertBatchWithRoleId(List<String> menus, String roleId);
}
