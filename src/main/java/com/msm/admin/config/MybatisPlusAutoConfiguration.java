package com.msm.admin.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.msm.admin.utils.SubjectUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author quavario@gmail.com
 * @date 2019/7/18 14:59
 */
@Configuration
@ConditionalOnClass(MybatisConfiguration.class)
public class MybatisPlusAutoConfiguration {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * field fill handler
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {

        class MyMetaObjectHandler implements MetaObjectHandler {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.setFieldValByName("createDate", new Date(), metaObject);
                this.setFieldValByName("updateDate", new Date(), metaObject);

                this.setFieldValByName("createBy", SubjectUtils.getCurrentUser().getId(), metaObject);
                this.setFieldValByName("updateBy", SubjectUtils.getCurrentUser().getId(), metaObject);

                if (!SubjectUtils.isAdmin() && metaObject.hasSetter("depId")) {
                    this.setFieldValByName("depId", SubjectUtils.getCurrentUser().getDepId(), metaObject);
                }

            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName("updateDate", new Date(), metaObject);
                this.setFieldValByName("updateBy", SubjectUtils.getCurrentUser().getId(), metaObject);
            }
        }
        return new MyMetaObjectHandler();
    }
}
