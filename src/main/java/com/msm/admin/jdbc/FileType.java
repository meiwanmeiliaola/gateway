package com.msm.admin.jdbc;


import java.util.Arrays;
import java.util.List;

public class FileType {

    private static final List<String> IMG_LIST= Arrays.asList(".xbm",".tif","pjp",".svgz","jpg","jpeg","ico","tiff",".gif","svg",".jfif",".webp",".png",".bmp","pjpeg",".avif");

    private static final List<String> AUDIO_LIST= Arrays.asList(".cda",".wav",".mp3",".aif",".aiff",".mid",".wma",".ra",".vqf",".ape");

    private static final List<String> PDF_LIST= Arrays.asList(".pdf");

    private static final List<String> VIDEO_LIST= Arrays.asList(".avi",".wmv",".mpg",".mpeg",".mov",".rm",".ram",".swf",".flv",".mp4");

    private static final List<String> ZIP_LIST= Arrays.asList(".html");

    public static boolean FLAG=false;

    public static String screen(String file){

        // 循环检测字符串后缀
        for (String s : IMG_LIST) {
            FLAG = file.endsWith(s);
            if (FLAG){
                return FileTypeEnum.IMAGE.getCode();
            }
        }

        for (String s : AUDIO_LIST) {
            FLAG = file.endsWith(s);
            if (FLAG){
                return FileTypeEnum.AUDIO.getCode();
            }
        }

        for (String s : PDF_LIST) {
            FLAG = file.endsWith(s);
            if (FLAG){
                return FileTypeEnum.PDF.getCode();
            }
        }

        for (String s : VIDEO_LIST) {
            FLAG = file.endsWith(s);
            if (FLAG){
                return FileTypeEnum.VIDEO.getCode();
            }
        }

        for (String s : ZIP_LIST) {
            FLAG = file.endsWith(s);
            if (FLAG){
                return FileTypeEnum.ZIP.getCode();
            }
        }
        
        return null;
    }

}
