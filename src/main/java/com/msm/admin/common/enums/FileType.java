package com.msm.admin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 文件上传支持的类型
 * @author quavario@gmail.com
 * @date 2020/5/20 14:35
 */
@AllArgsConstructor
public enum FileType {
    /**
     * JEPG.
     */
    JPEG("FFD8FF"),

    /**
     * PNG.
     */
    PNG("89504E47"),

    /**
     * GIF.
     */
    GIF("47494638"),


    /**
     * Windows Bitmap.
     */
    BMP("424D"),



    /**
     * Adobe Acrobat.
     */
    PDF("255044462D312E"),


    /**
     * ZIP Archive.
     */
    ZIP("504B0304"),

    /**
     * RAR Archive.
     */
//    RAR("52617221"),

    /**
     * Wave.
     */
    WAV("57415645"),

    /**
     * AVI.
     */
    AVI("41564920"),

    /**
     * MPEG (mpg).
     */
    MPG("000001BA"),

    /**
     * MP3
    */
    MP3("49443304"),

    /**
     * Quicktime.
     */
    MOV("6D6F6F76");

    private String value = "";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}