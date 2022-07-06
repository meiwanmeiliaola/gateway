package com.msm.admin.modules.common.controller;

import com.msm.admin.common.enums.ErrorInfo;
import com.msm.admin.common.enums.FileType;
import com.msm.admin.config.FileUploadProperties;
import com.msm.admin.modules.common.entity.SysFile;
import com.msm.admin.modules.common.service.SysFileService;
import com.msm.admin.utils.DeCompressUtil;
import com.msm.admin.utils.ExceptionThrower;
import com.msm.admin.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author quavario@gmail.com
 * @date 2019/10/17 11:00
 */
@RequestMapping("/upload")
@RestController
@Slf4j
public class SysFileController {

    static final String password = "msmkeystore";
    static final String alias = "msmkey";
    static final String keyPass = "msmkeypass";
    static final String STORE_PATH = "msm.keystore";

    @Autowired
    private FileUploadProperties fileUploadProperties;

    @Autowired
    private SysFileService sysFileService;

    @RequestMapping
    public Object save(MultipartFile file) throws Exception {
        // 校验文件类型
        FileType fileType = FileUtil.getType(file.getInputStream());
        if (fileType == null) {
            ExceptionThrower.of(ErrorInfo.UNSUPPORTED_FILE_TYPE);
        }

        // zip文件验签
/*        if (FileType.ZIP.equals(fileType)) {
            log.info("验证文件{}", file.getName());
            boolean verify = verify(file);
            if (!verify) {
                ExceptionThrower.of(ErrorInfo.UNSIGNED_FILE);
            }
            log.info("验证通过 {}", file.getName());
        }*/

        // 上传
        SysFile sysFile = convertMultipartFileToSysFile(file);
        File realFile = new File(sysFile.getLocalPath());
        file.transferTo(realFile);

        // 解压
        if (FileType.ZIP.equals(fileType)) {


            new Thread(() -> {
                try {
                    log.info("开始解压....");
                    log.info("绝对路径:"+sysFile.getLocalPath()+";destDir:"+fileUploadProperties.getMappingPath() + "/" + fileUploadProperties.getApplicationName() + "/zip/" + sysFile.getUniName().replace(".zip", ""));
                    DeCompressUtil.deCompress(sysFile.getLocalPath(),
                            fileUploadProperties.getMappingPath() + "/" + fileUploadProperties.getApplicationName() + "/zip/" + sysFile.getUniName().replace(".zip", "")
                    );
                    log.info("解压完成");
                    log.info("解压路径{}", sysFile.getLocalPath());
                    // 删除压缩文件
                    realFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();


//            /data/zip/fe07dc68-f7b1-4d32-ae1f-8123fe117383/index.html
//            E:/Dev/msm_data_mapping/msm-admin/zip/fe07dc68-f7b1-4d32-ae1f-8123fe117383.zip
            // 异步解压移除此代码
//            if (!new File(sysFile.getLocalPath().replace(".zip", "") + "/index.html").exists()) {
//                ExceptionThrower.of(ErrorInfo.UNFORMAT_ARCHIVE);
//            }
        }

        sysFileService.save(sysFile);

        return sysFile;
    }

    @RequestMapping("/ckeditor")
    public Object ckeUpload(MultipartFile file) throws Exception {
        SysFile sysFile = new SysFile();
        String strType = FileUtil.getStrType(file);

        sysFile.setOriName(file.getOriginalFilename());
        sysFile.setUniName(FileUtil.toUniFileName(file));
        sysFile.setLocalPath(
                fileUploadProperties.getMappingPath()
                        + "/" + fileUploadProperties.getApplicationName()
                        + "/" + strType + "/"
                        + sysFile.getUniName()

        );

        String urlPath = fileUploadProperties.getUrl() + "/" + strType + "/" + sysFile.getUniName();


        // set url path
        sysFile.setUrlPath(urlPath);
        // set size with kb
        sysFile.setSize(file.getSize() / 1024);

        File realFile = new File(sysFile.getLocalPath());
        //开始上传
        file.transferTo(realFile);
        sysFileService.save(sysFile);

        Map<String, String> map = new HashMap<>();
        map.put("url", sysFile.getUrlPath());
        return map;
    }



    @DeleteMapping
    public boolean del(String url, String type) {
        return sysFileService.del(url, type);
    }

    /**
     * convert MultipartFile to SysFile
     * @param file multipart file
     * @return SysFile
     */
    SysFile convertMultipartFileToSysFile(MultipartFile file) throws IOException {
        FileType fileType = FileUtil.getType(file.getInputStream());

        SysFile sysFile = new SysFile();
        System.out.println(file.getContentType());
        String strType = FileUtil.getStrType(file);

        sysFile.setOriName(file.getOriginalFilename());
        sysFile.setUniName(FileUtil.toUniFileName(file));
        sysFile.setLocalPath(
                fileUploadProperties.getMappingPath()
                        + "/" + fileUploadProperties.getApplicationName()
                        + "/" + strType + "/"
                        + sysFile.getUniName()

        );

        String urlPath = null;
        if (FileType.ZIP.equals(fileType)) {
            urlPath = fileUploadProperties.getUrl() + "/" + strType + "/"
                    + FileUtil.getFileNameNoExt(sysFile.getUniName()) + "/index.html";
        } else {
            urlPath = fileUploadProperties.getUrl() + "/" + strType + "/" + sysFile.getUniName();
        }

        sysFile.setUrlPath(urlPath);
        // set size with kb
        sysFile.setSize(file.getSize() / 1024);
        return sysFile;
    }

    /**
     * 校验文件是否签名,
     * @return true
     */
    public boolean verify(MultipartFile file) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(File.separator + STORE_PATH);
            InputStream storeFileIs = classPathResource.getInputStream();


            // TODO 如果文件过大 可导致内存溢出，可以使用签名hex


            byte[] signed = new byte[256];
            InputStream fileIs = file.getInputStream();

            // 载入keystore
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(storeFileIs, password.toCharArray());

            Certificate certificate = ks.getCertificate(alias);
            PublicKey publicKey = certificate.getPublicKey();


            Signature v = Signature.getInstance("SHA1withRSA");
            v.initVerify(publicKey);

            int len;
            // 待验签文件大小
            long sighedFileSize = file.getSize();
            long noSignedFileSize = sighedFileSize - 256;
            // 已读文件大小
            long readSize = 0;
            // 待读数组长度
            long lastByteSize = sighedFileSize - readSize;
            byte[] bytes = new byte[1024];

            // 是否将结束
            byte[] tempBytes = new byte[1024];
            while ((len = fileIs.read(bytes)) != -1) {
                readSize += 1024;
                lastByteSize = sighedFileSize - readSize;
                if (len != 1024) {
                    if (len > 256) {
                        // 读取的数组长度大于256 截取最后的256个字节
                        v.update(bytes, 0, len - 256);
                        int j = 0;
                        for (int i = len - 256; i < len; i ++) {
                            signed[j] = bytes[i];
                            j++;
                        }
                    } else {
                        // 不足256 截取tempBytes的最后256-len个字节 然后加入bytes
                        int j = 0;
                        for (int i = 1024 - (256 - len); i < 1024; i++) {
                            signed[j] = tempBytes[i];
                            j++;
                        }

                        for (int i = 0; i < len; i++) {
                            signed[j] = bytes[i];
                            j++;
                        }
                    }
                } else {
                    if (lastByteSize < 256) {
                        // 下次读取不足256，说明本次读取的数组中有签名bytes，update要避开签名bytes
                        v.update(bytes, 0, len - (256 - (int)lastByteSize));
                    } else {
                        v.update(bytes, 0, len);
                    }
                }

                tempBytes = bytes;
                bytes = new byte[1024];

            }

            boolean verify = v.verify(signed);
            System.out.println(verify);
            return verify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    public boolean verify2(InputStream is) {
        try {
            byte[] signedFileBytes;

            // TODO 如果文件过大 可导致内存溢出，可以使用签名hex
            signedFileBytes = IOUtils.toByteArray(is);

            byte[] oriFileBytes = new byte[signedFileBytes.length - 256];

            byte[] signed = new byte[256];

            int position = 0;
            for (int i = 0; i < signedFileBytes.length; i++) {
                if (i < signedFileBytes.length -256) {
                    oriFileBytes[i] = signedFileBytes[i];
                } else {
                    signed[position] = signedFileBytes[i];
                    position++;
                }
            }

            ClassPathResource classPathResource = new ClassPathResource(File.separator + STORE_PATH);
            InputStream storeFileIs = classPathResource.getInputStream();


//            File storeFile = ResourceUtils.getFile("classpath:" + STORE_PATH);
//            FileInputStream storeFileIs = new FileInputStream(storeFile);

            // 载入keystore
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(storeFileIs, password.toCharArray());

            Certificate certificate = ks.getCertificate(alias);
            PublicKey publicKey = certificate.getPublicKey();


            Signature v = Signature.getInstance("SHA1withRSA");
            v.initVerify(publicKey);
            v.update(oriFileBytes);
            boolean verify = v.verify(signed);
            return verify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
