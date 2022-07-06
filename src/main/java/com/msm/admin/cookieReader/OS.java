package com.msm.admin.cookieReader;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author quavario@gmail.com
 * @date 2021/3/8 11:11
 */
public class OS {
    public static String getOsArchitecture() {
        return System.getProperty("os.arch");
    }

    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }

    public static String getOperatingSystemVersion() {
        return System.getProperty("os.version");
    }

    public static String getIP() throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }

    public static String getHostname() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    public static boolean isWindows() {
        return (getOperatingSystem().toLowerCase().indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (getOperatingSystem().toLowerCase().indexOf("mac") >= 0);
    }

    public static boolean isLinux() {
        return (getOperatingSystem().toLowerCase().indexOf("nix") >= 0 || getOperatingSystem().toLowerCase().indexOf("nux") >= 0 || getOperatingSystem().toLowerCase().indexOf("aix") > 0 );
    }

    public static boolean isSolaris() {
        return (getOperatingSystem().toLowerCase().indexOf("sunos") >= 0);
    }
}
