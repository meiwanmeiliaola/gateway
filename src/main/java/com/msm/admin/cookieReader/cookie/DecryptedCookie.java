package com.msm.admin.cookieReader.cookie;

import java.io.File;
import java.util.Date;

/**
 * @author quavario@gmail.com
 * @date 2021/3/8 11:09
 */
public class DecryptedCookie extends Cookie {
    private String decryptedValue;

    public DecryptedCookie(String name, byte[] encryptedValue, String decryptedValue, Date expires, String path, String domain, boolean secure, boolean httpOnly, File cookieStore) {
        super(name, encryptedValue, expires, path, domain, secure, httpOnly, cookieStore);
        this.decryptedValue = decryptedValue;
    }

    public String getDecryptedValue(){
        return decryptedValue;
    }

    @Override
    public boolean isDecrypted() {
        return true;
    }

    @Override
    public String toString() {
        return "Cookie [name=" + name + ", value=" + decryptedValue + "]";
    }
}
