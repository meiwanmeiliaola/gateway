package com.msm.admin.framework.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author quavario@gmail.com
 * @date 2020/4/8 11:40
 */
@Deprecated
public class ExceptionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
