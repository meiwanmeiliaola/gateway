package com.msm.admin.modules.common.entity;

import com.msm.admin.modules.system.entity.SysUser;
import lombok.Data;

/**
 * @author quavario@gmail.com
 * @date 2019/10/25 10:29
 */
@Data
public class UserInfo {
    private String name;
    private String loginName;
    private String id;
    private String avatar;
    private String email;

    public static UserInfo build(SysUser user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setEmail(user.getEmail());
        userInfo.setName(user.getName());
        userInfo.setLoginName(user.getLoginName());
        return userInfo;
    }
}
