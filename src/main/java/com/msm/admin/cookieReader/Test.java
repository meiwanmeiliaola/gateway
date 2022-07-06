package com.msm.admin.cookieReader;

import com.msm.admin.cookieReader.browser.ChromeBrowser;
import com.msm.admin.cookieReader.cookie.Cookie;

import java.io.UnsupportedEncodingException;
import java.util.Set;
/**
 * @author quavario@gmail.com
 * @date 2021/3/8 11:15
 */
public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        ChromeBrowser chrome = new ChromeBrowser();
        Set<Cookie> cookies = chrome.getCookiesForDomain(".x.yupoo.com");
        for(Cookie cookie : cookies){
            System.out.println(new String(cookie.getEncryptedValue()));
        }
    }
}
