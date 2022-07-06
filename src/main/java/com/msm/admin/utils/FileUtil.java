package com.msm.admin.utils;

import com.msm.admin.common.enums.FileType;
import com.msm.admin.config.FileUploadProperties;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author quavario@gmail.com
 * @date 2019/3/5 16:52
 */
public class FileUtil {



    /**
     * 解压缩
     * @param sourceFile
     * @param destDir
     */
    public static void deCompress(String sourceFile,String destDir){

    }

    /**
     * 将文件头转换成16进制字符串
     *
     * @param
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src){

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private static String getFileContent(String filePath) throws IOException {

        byte[] b = new byte[28];

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(filePath);
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return bytesToHexString(b);
    }

    /**
     * 判断文件类型
     *
     * @param filePath 文件路径
     * @return 文件类型
     */
    public static FileType getType(String filePath) throws IOException {

        String fileHead = getFileContent(filePath);

        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }

        fileHead = fileHead.toUpperCase();

        FileType[] fileTypes = FileType.values();

        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }

        return null;
    }

    /**
     * 判断文件类型
     *
     * @param inputStream 源
     * @return 文件类型
     */
    public static FileType getType(InputStream inputStream) throws IOException {

        byte[] b = new byte[28];

        try {
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        String fileHead = bytesToHexString(b);

        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }

        fileHead = fileHead.toUpperCase();

        FileType[] fileTypes = FileType.values();

        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }

        return null;
    }



    public static String toUniFileName(MultipartFile file){

        String fileName = null;
        String realFileName = null;
        try{
            //原始文件名
             fileName = file.getOriginalFilename();
            //文件名后缀
            String suffix = "";
            if (fileName.contains(".")){
                suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
            }

            //没有后缀的文件名
            String realFileNameNoExt = UUID.randomUUID().toString();
            //实际文件名z
            realFileName = realFileNameNoExt + suffix;
            return realFileName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取不带扩展名的文件名
     * @param fileName
     * @return
     */
    public static String getFileNameNoExt(String fileName){
        if (fileName.contains(".")){
            return fileName.substring(0,fileName.lastIndexOf("."));
        }
        return null;
    }


    public static String getStrType(MultipartFile file) {
        return com.msm.admin.modules.common.constant.FileType.list.stream().filter(type -> file.getContentType().contains(type)).findFirst().orElse(null);
    }




}
