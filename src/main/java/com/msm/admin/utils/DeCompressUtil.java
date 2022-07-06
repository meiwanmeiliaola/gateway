package com.msm.admin.utils;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author quavario@gmail.com
 * @date 2019/3/5 19:35
 */
public class DeCompressUtil {
    /**
     * 解压zip格式压缩包
     * 对应的是ant.jar
     */
    private static void unzip(String sourceZip,String destDir) throws Exception{
        try{
            Project p = new Project();
            Expand e = new Expand();
            e.setProject(p);
            e.setSrc(new File(sourceZip));
            e.setOverwrite(false);
            e.setDest(new File(destDir));
           /*
           ant下的zip工具默认压缩编码为UTF-8编码，
           而winRAR软件压缩是用的windows默认的GBK或者GB2312编码
           所以解压缩时要制定编码格式
           */
            e.setEncoding("gbk");
            e.execute();
        }catch(Exception e){
            throw e;
        }
    }
    /**
     * 解压rar格式压缩包。
     * 对应的是java-unrar-0.3.jar，但是java-unrar-0.3.jar又会用到commons-logging-1.1.1.jar
     */
    private static void unrar(String sourceRar,String destDir) throws Exception{
        Archive a = null;
        FileOutputStream fos = null;
        try{
            a = new Archive(new File(sourceRar));
            FileHeader fh = a.nextFileHeader();
            while(fh!=null){
                if(!fh.isDirectory()){
                    //1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
                    String compressFileName = fh.getFileNameString().trim();
                    String destFileName = "";
                    String destDirName = "";
                    //非windows系统
                    if(File.separator.equals("/")){
                        destFileName = destDir + compressFileName.replaceAll("\\\\", "/");
                        destDirName = destFileName.substring(0, destFileName.lastIndexOf("/"));
                        //windows系统
                    }else{
                        destFileName = destDir + compressFileName.replaceAll("/", "\\\\");
                        destDirName = destFileName.substring(0, destFileName.lastIndexOf("\\"));
                    }
                    //2创建文件夹
                    File dir = new File(destDirName);
                    if(!dir.exists()||!dir.isDirectory()){
                        dir.mkdirs();
                    }
                    //3解压缩文件
                    fos = new FileOutputStream(new File(destFileName));
                    a.extractFile(fh, fos);
                    fos.close();
                    fos = null;
                }
                fh = a.nextFileHeader();
            }
            a.close();
            a = null;
        }catch(Exception e){
            throw e;
        }finally{
            if(fos!=null){
                try{fos.close();fos=null;}catch(Exception e){e.printStackTrace();}
            }
            if(a!=null){
                try{a.close();a=null;}catch(Exception e){e.printStackTrace();}
            }
        }
    }
    /**
     * 解压缩
     */
    public static void deCompress(String sourceFile,String destDir) throws Exception{
        //保证文件夹路径最后是"/"或者"\"
        char lastChar = destDir.charAt(destDir.length()-1);
        if(lastChar!='/'&&lastChar!='\\'){
            destDir += File.separator;
        }
        //根据类型，进行相应的解压缩
        String type = sourceFile.substring(sourceFile.lastIndexOf(".")+1);
        if(type.equals("zip")){
            DeCompressUtil.unzip(sourceFile, destDir);
        }else if(type.equals("rar")){
            DeCompressUtil.unrar(sourceFile, destDir);
        }else{
            throw new Exception("只支持zip和rar格式的压缩包！");
        }
    }

    public static void main(String[] args) throws Exception {
        Path inputPath = Paths.get("C:\\Users\\quam\\Desktop\\signed_pano.zip");
        Path outputPath = Paths.get("C:\\Users\\quam\\Desktop\\signed_pano");

        InputStream fin = Files.newInputStream(inputPath);
        OutputStream out = Files.newOutputStream(outputPath);

//        new GzipCompressorInputStream()
        ZipArchiveInputStream in = new ZipArchiveInputStream(new BufferedInputStream(fin));
        final byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
    }

    public static void unzip2(String sourceZip,String destDir) {

    }
}
