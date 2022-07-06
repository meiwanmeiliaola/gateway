package com.msm.admin.jdbc;

import lombok.Getter;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/5/6 15:03
 */
@Getter
public enum FileTypeEnum {

    /**
     * 音频
    */
    AUDIO("1","音频"),

    /**
     * 图片
    */
    IMAGE("2","图片"),

    /**
     * PDF文件
    */
    PDF("3","PDF文件"),

    /**
     * 视频
    */
    VIDEO("4","视频"),

    /**
     * 压缩包
    */
    ZIP("5","压缩包");


    private final String code;

    private final String name;


    FileTypeEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

}
