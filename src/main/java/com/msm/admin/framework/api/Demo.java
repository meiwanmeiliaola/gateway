package com.msm.admin.framework.api;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author quavario@gmail.com
 * @date 2020/5/28 14:46
 */
public class Demo {
    public static void main(String[] args) {
        String hashpw = BCrypt.hashpw("hma-180628", BCrypt.gensalt());
        System.out.println(hashpw);
    }
}
