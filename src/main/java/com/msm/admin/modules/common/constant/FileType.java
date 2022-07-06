package com.msm.admin.modules.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author quavario@gmail.com
 * @date 2019/4/18 15:28
 */
public class FileType {
//    public static final String[] list = new String[]{"image", "data", "video"};

    public static final List<String> list = new ArrayList<>();
    public static final Integer VIDEO = 3;
    public static final Integer IMAGE = 1;
    public static final Integer ZIP = 2;
    static {
        list.add("image");
        list.add("zip");
        list.add("video");
        list.add("pdf");
        list.add("audio");
    }
}
