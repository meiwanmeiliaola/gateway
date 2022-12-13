package com.msm.admin.config;

import com.msm.admin.modules.system.service.SysMenuService;
import com.msm.admin.modules.system.service.SysUserService;
import com.msm.admin.security.BCryptCredentialsMatcher;
import com.msm.admin.security.ShiroLoginFilter;
import com.msm.admin.security.UserRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author quavario@gmail.com
 * @date 2019/12/11 17:25
 */
@Configuration
public class  ShiroConfig {

    /**
     * cookie对象
     *
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置cookie的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(86400);
        return cookie;
    }

    /**
     * cookie管理对象
     *
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberme cookie加密的密钥 建议每个项目都不一样 默认AES算法     密钥长度（128 256 512 位），通过以下代码可以获取
        // KeyGenerator keygen = KeyGenerator.getInstance("AES");
        // SecretKey deskey = keygen.generateKey();
        // System.out.println(Base64.encodeToString(deskey.getEncoded()));
        cookieRememberMeManager.setCipherKey(Base64.decode("vXP33dDAonIp9bFwGl7aT7rA=="));
        return cookieRememberMeManager;
    }
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        return new BCryptCredentialsMatcher();
    }
    @Bean
    public Realm realm(SysUserService sysUserService, SysMenuService menuService) {
        UserRealm userRealm = new UserRealm(sysUserService, menuService);
        userRealm.setCredentialsMatcher(credentialsMatcher());
        return userRealm;
    }

    /**
     * 解决requirePermission后访问url 404问题
     * @return
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){

        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);

        return defaultAdvisorAutoProxyCreator;
    }


    @Bean
    public DefaultWebSecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1000 * 60 * 60 * 24);

        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean
    public BCryptCredentialsMatcher bcrypt() {
        return new BCryptCredentialsMatcher();
    }


    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);


        //用户未登录不进行跳转，而是自定义返回json数据
        Map<String, Filter> filters = bean.getFilters();//获取filters
        filters.put("user", new ShiroLoginFilter());//将自定义 的FormAuthenticationFilter注入shiroFilter中


//        bean.setSuccessUrl("/index");
//        bean.setUnauthorizedUrl("/unauthorizedurl");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/auth/**", "anon");
        map.put("/v1/**", "anon");
        map.put("/data/**", "anon");
        map.put("/watermark/data/**", "anon");
        map.put("/watermark/file/**", "anon");
        map.put("/file/**", "anon");
        map.put("/image/**", "anon");
        map.put("/relics/**", "anon");
        map.put("/gwRelic/**", "anon");
        map.put("/gwInfo/**", "anon");
        map.put("/gwRoam/**", "anon");
        map.put("/excel/**", "anon");
        map.put("/gwNum/**", "anon");
        map.put("/collection/**", "anon");
        map.put("/gwExp/**", "anon");
        map.put("/demo/**", "anon");
        map.put("/relicCategories/**", "anon");
        map.put("/overview/**", "anon");
        map.put("/culture/**", "anon");
        map.put("/static/**", "anon");
        map.put("/**", "user");

//        map.put("/**", "authc");

        bean.setFilterChainDefinitionMap(map);
        return bean;
    }


}
