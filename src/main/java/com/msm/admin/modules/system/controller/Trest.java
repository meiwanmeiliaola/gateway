package com.msm.admin.modules.system.controller;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author quavario@gmail.com
 * @date 2021/2/20 11:50
 */
public class Trest {
    public static void main(String[] args) {
        String asf = BCrypt.hashpw("asf", "$2a$10$jNHBa2paqlvPJG8UyLjwbe54XRrJnKzBnpx4lccYDTgMy9OFB9uY2");
        System.out.println(asf);
    }
}
