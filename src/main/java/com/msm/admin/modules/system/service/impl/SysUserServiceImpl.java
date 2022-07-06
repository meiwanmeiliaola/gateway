package com.msm.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msm.admin.common.enums.ErrorInfo;
import com.msm.admin.framework.exception.NameIsTakenException;
import com.msm.admin.framework.exception.UserNameIsTakenException;
import com.msm.admin.modules.common.entity.TreeData;
//import com.msm.admin.modules.common.service.impl.RedisService;
import com.msm.admin.modules.system.entity.SysMenu;
import com.msm.admin.modules.system.entity.SysRole;
import com.msm.admin.modules.system.entity.SysUser;
import com.msm.admin.modules.system.mapper.SysUserMapper;
import com.msm.admin.modules.system.service.SysMenuService;
import com.msm.admin.modules.system.service.SysRoleService;
import com.msm.admin.modules.system.service.SysUserRoleService;
import com.msm.admin.modules.system.service.SysUserService;
import com.msm.admin.utils.ExceptionThrower;
import com.msm.admin.utils.SubjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.msm.admin.common.enums.ErrorInfo.*;


/**
 * @Description: 用户
 * @Author: quavario
 * @Date:   2019-05-23
 * @Version: V1.0
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Autowired
    @Lazy
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private HttpSession session;

//    @Autowired
//    private RedisService redisService;
    @Autowired
    public SysUserRoleService userRoleService;

    /**
     * @param username 登录名loginName
     * @return 用户
     * @throws org.apache.ibatis.exceptions.TooManyResultsException
     */
    @Override
    public SysUser getByUsername(String username) {
        SysUser sysUser = super.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getLoginName, username)
                .select(SysUser::getPassword, SysUser::getDepId, SysUser::getId, SysUser::getName, SysUser::getLoginName, SysUser::getAvatar, SysUser::getType, SysUser::getStatus));
        return sysUser;
    }

    @Override
    public IPage pageSearch(Page<SysUser> page, SysUser sysUser) {

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();

        if (!SubjectUtils.isAdmin()) {
            queryWrapper.eq("create_by", SubjectUtils.getCurrentUser().getId());
        }

        if (!StringUtils.isEmpty(sysUser.getDepId())) {
            queryWrapper.eq("dep_id", sysUser.getDepId());
        }
        if (!StringUtils.isEmpty(sysUser.getName())) {
            queryWrapper.like("name", sysUser.getName());
        }

        queryWrapper.orderByDesc("update_date", "create_date");

        return super.page(page, queryWrapper);
    }

    @Override
    public void delAvatar(String userId) {
    }

    /**
     * @param name 昵称 name
     * @return 用户
     */
    @Override
    public SysUser getByName(String name) {
        SysUser sysUser = super.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getName, name).eq(SysUser::getStatus, 1)
                .select(SysUser::getPassword, SysUser::getId, SysUser::getName, SysUser::getLoginName, SysUser::getAvatar, SysUser::getType));
        return sysUser;
    }

    @Override
    public Map userInfo() {
        // user
        SysUser currentUser = SubjectUtils.getCurrentUser();
        if (currentUser == null) {
            return null;
        }
       /* String sessionId = session.getId();
        String redisKey = new StringBuilder("user:userId-").append(currentUser.getId()).append(":").append(sessionId).toString();
        if (redisService.hasKey(redisKey)) {
            log.info("缓存命中");
            return (Map) redisService.get(redisKey);
        }*/

        Map<String, Object> result = new HashMap<>();



        // menus
        List<SysMenu> userMenus;
        if (currentUser.getType() == 1) {
            userMenus = sysMenuService.list(
                    Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getType, 1).or()
                            .eq(SysMenu::getType, 2).orderByAsc(SysMenu::getSort)
            );
            userMenus = TreeData.toTreeList(userMenus);
        } else {
            userMenus = sysMenuService.listByUser(currentUser.getId());
        }

        // user role
        List<SysRole> userRoles = sysRoleService.listByUser(currentUser.getId());

        List<String> permissions = sysMenuService.listPermissionByUser(currentUser.getId());


        // user
        SysUser dbUser = getById(currentUser.getId());

        // add user information to map
        dbUser.setPassword(null);
        result.put("user", dbUser);
        result.put("menus", userMenus);
        result.put("roles", userRoles);
        result.put("permissions", permissions);


//        log.info("缓存未命中");
//        log.info("添加新缓存");
//        redisService.set(redisKey, result);
        return result;
    }

    @Override
    public void resetPw(String pw, String newPw, String comfirmPw) {
        SysUser sysUser= getByUsername(SubjectUtils.getCurrentUser().getLoginName());
        // 校验
        boolean checkpw = BCrypt.checkpw(pw, sysUser.getPassword());
        if (checkpw && newPw.equals(comfirmPw)) {
            SysUser user = new SysUser();
            user.setId(sysUser.getId());
            user.setPassword(newPw);
            updateById(user);
        }
    }

    /**
     * @param id 用户ID
     * @return 用户名称
     */
    @Override
    public String getNameById(String id) {
        SysUser user = super.getOne(Wrappers.<SysUser>lambdaQuery().select(SysUser::getName).eq(SysUser::getId, id));

        if (user != null && user.getName() != null) {
            return user.getName();
        }
        return null;
    }

    @Override
    public String freezeUser(String id) {
        SysUser user = getById(id);
        if (user == null) {
            ExceptionThrower.of(ErrorInfo.UNKNOWN_USER);
        } else {
            user.setStatus(2);
            super.updateById(user);
        }

        return "成功冻结用户" + user.getName();
    }

    @Override
    public String unFreezeUser(String id) {
        SysUser user = getById(id);
        if (user == null) {
            ExceptionThrower.of(ErrorInfo.UNKNOWN_USER);
        } else {
            user.setStatus(1);
            super.updateById(user);
        }

        return "成功解冻用户" + user.getName();
    }

    @Override
    public SysUser getById(Serializable id) {
        SysUser dbUser = super.getById(id);


        // query roles
        List<String> roles = roleService.listByUser(dbUser.getId()).stream().map(SysRole::getId).collect(Collectors.toList());
        dbUser.setRoles(roles);
        return dbUser;
    }




    @Override
    public boolean saveOrUpdate(SysUser entity) {

//        Assert.isTrue(SubjectUtils.isAdmin(), "");
        boolean isNewUser = entity.getId() == null || entity.getId().length() == 0;

        saveCheck(entity);
        /*if (!isNewUser && entity.getType() != 1 && getCountOfAdmin() == 1) {
            log.error("至少需要一个管理员用户");
            return false;
        }*/

        /*if (isNewUser) {
            entity.setPassword(BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt(12)));
        }*/

        if (!StringUtils.isEmpty(entity.getPassword())) {
            String hashpw = BCrypt.hashpw(entity.getPassword(), "$2a$10$jNHBa2paqlvPJG8UyLjwbe54XRrJnKzBnpx4lccYDTgMy9OFB9uY2");
            entity.setPassword(hashpw);
        }
        super.saveOrUpdate(entity);

        if (isNewUser) {
            userRoleService.addBatchWithUserId(entity.getRoles(), entity.getId());

        } else {
            userRoleService.updateBatchWithUserId(entity.getRoles(), entity.getId());
        }
        return true;
    }


    @Override
    public boolean removeById(Serializable id) {
        SysUser user = getById(id);

        if (user.getType() == 1 && getCountOfAdmin() == 1) {
            log.info("至少需要一个管理员用户");
            return false;
        }

        return super.removeById(id);
    }

    Integer getCountOfAdmin() {
        int count = super.count(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getType, 1));
        return count;
    }

    Boolean isNewUser(SysUser user) {
        return user.getId() == null || "".equals(user.getId());
    }
//    Boolean checkLoginName(SysUser user) {
//    }

    /**
     * 用户校验
     * @param user 待校验用户
     */
    /*public void checkUser(SysUser user) {
        boolean isNewUser = user.getId() == null || user.getId().length() == 0;

        SysUser userNameUser = getByUsername(user.getLoginName());
        SysUser nameUser = getByName(user.getName());

        *//**
         * 用户校验
         * 1： 添加用户直接校验
         * 2： 编辑用户名排除自己
         *//*

        *//*if (isNewUser) {
            // 校验登录名
            Optional.ofNullable(userNameUser).ifPresent(u -> ExceptionThrower.of(USER_NAME_IS_TAKEN));
            if (userNameUser != null) {
                throw new UserNameIsTakenException("username has been token");
            }

            // TODO 修改if判断改为Optional
            // 校验昵称
//            Optional.ofNullable(nameUser).ifPresent(u -> ExceptionThrower.of(NAME_IS_TAKEN));
            if (nameUser != null) {
                throw new NameIsTakenException("name has been token");
            }
        } else {

            // 校验登录名
            if (userNameUser != null && !userNameUser.getId().equals(user.getId())) {
                throw new UserNameIsTakenException("username has been token");
            }

            // 校验昵称
            if (userNameUser != null && !nameUser.getId().equals(user.getId())) {
                throw new NameIsTakenException("name has been token");
            }
        }
*//*

        if (isNewUser) {
            // 校验登录名
            Optional.ofNullable(userNameUser).ifPresent(u -> ExceptionThrower.of(USER_NAME_IS_TAKEN));


            // TODO 修改if判断改为Optional
            // 校验昵称
            Optional.ofNullable(nameUser).ifPresent(u -> ExceptionThrower.of(NAME_IS_TAKEN));

        } else {

            // 校验登录名
            if (userNameUser != null && !userNameUser.getId().equals(user.getId())) {
                throw new UserNameIsTakenException("username has been token");
            }

            // 校验昵称
            if (userNameUser != null && !nameUser.getId().equals(user.getId())) {
                throw new NameIsTakenException("name has been token");
            }
        }


    }*/

    /**
     * 用户校验
     * @param user 待校验用户
     */
    public void saveCheck(SysUser user) {
        checkName(user);
        checkLoginName(user);
    }

    /**
     * 校验不通过抛出异常
     * @param user 校验的用户
     */
    private void checkName(SysUser user) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();

        // 编辑操作校验时排除自身
        Optional.ofNullable(user.getId()).ifPresent(id -> {
            queryWrapper.ne(SysUser::getId, id);
        });

        Optional.ofNullable(getOne(queryWrapper.eq(SysUser::getName, user.getName()).select(SysUser::getName)))
                .ifPresent(u -> ExceptionThrower.of(NAME_IS_TAKEN));
    }

    private void checkLoginName(SysUser user) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();

        // 编辑操作校验时排除自身
        Optional.ofNullable(user.getId()).ifPresent(id -> {
            queryWrapper.ne(SysUser::getId, id);
        });

        Optional.ofNullable(getOne(queryWrapper.eq(SysUser::getLoginName, user.getLoginName()).select(SysUser::getLoginName)))
                .ifPresent(u -> ExceptionThrower.of(USER_NAME_IS_TAKEN));
    }
}
