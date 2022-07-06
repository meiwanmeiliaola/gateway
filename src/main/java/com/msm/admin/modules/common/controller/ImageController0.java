package com.msm.admin.modules.common.controller;

import com.msm.admin.config.FileUploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author quavario@gmail.com
 * @date 2021/5/26 9:09
 */
@Controller
@RequestMapping("/watermark/file")
@Slf4j
public class ImageController0 {
    private static String logo = "/image/logo3.png";
    private static BufferedImage logoBufferImage;

    static {
        try {
            logoBufferImage = ImageIO.read(new File("/data/logo3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int scalePercent = 25;

    @Resource
    private HttpServletResponse response;



    @RequestMapping(value = "/relic/**", produces = MediaType.ALL_VALUE)
    @ResponseBody
    public void image(HttpServletRequest request) throws IOException {
//        return ImageIO.read(new FileInputStream(new File("E:\\Dev\\git_repository\\msm\\msm-admin\\src\\main\\resources\\image\\logo3.png")));

        String fileDir = "/data/museum/data-mapping/msm_admin_static/static/file";
        String filePath = fileDir + request.getRequestURI().replace("/watermark/file", "");
        OutputStream os = null;
        try {
//        读取图片
            File sourceFile = new File(filePath);
            if (!sourceFile.exists()) {
                throw new RuntimeException("找不到文件" + filePath);
            }
            response.setContentType(Files.probeContentType(Paths.get(filePath)));
            os = response.getOutputStream();

            if (sourceFile.exists()) {
                String path = this.getClass().getResource(logo).getPath();
                File logoFile = new File(path);

                if (!sourceFile.isFile()) {
                    throw new RuntimeException("找不到文件");
                }

                //将icon加载到内存中
                log.info("logo文件地址为{}", path);
                //icon高度
                int logoImageHeight = logoBufferImage.getHeight(null);
                int logoImageWidth = logoBufferImage.getWidth(null);

                //将源图片读到内存中
                BufferedImage sourceBufferImage = ImageIO.read(sourceFile);

                // 原图片宽
                int sourceImageWidth = sourceBufferImage.getWidth(null);
                // 原图片高
                int sourceImageHeight = sourceBufferImage.getHeight(null);

                BufferedImage bi = new BufferedImage(sourceImageWidth,sourceImageHeight,BufferedImage.TYPE_INT_RGB);
                //创建一个指定 BufferedImage 的 Graphics2D 对象
                Graphics2D g = bi.createGraphics();

                // logo占原图的百分比 这是设置为20，即20%

                double scaledHeight = sourceImageHeight * 0.25;
                // 获取缩放比
                Double scale = scaledHeight / logoImageHeight;

                //x,y轴默认是从0坐标开始
                int x = (int) ((sourceImageWidth / 2) - (logoImageWidth * scale / 2));
                int y = (int) ((sourceImageHeight / 2) - (logoImageHeight * scale / 2));

                //设置对线段的锯齿状边缘处理
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                //呈现一个图像，在绘制前进行从图像空间到用户空间的转换
                g.drawImage(sourceBufferImage,0,0,null);

                //水印图象的路径 水印一般为gif或者png的，这样可设置透明度
                //得到Image对象。
                //透明度，最小值为0，最大值为1
//            float clarity = 0.6f;
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,clarity));
                //表示水印图片的坐标位置(x,y)
//            g.drawImage(con, 300, 220, null);

                g.drawImage(scaleBufferImage(logoBufferImage, scale), x, y, null);


                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
                g.dispose();
                ImageIO.write(bi, "png", os);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }

    }
    /**
     * @param sourceBufferImage 原图
     * @param scale 缩放比例
     * @return bufferImage缩放
     */
    private static BufferedImage scaleBufferImage(BufferedImage sourceBufferImage, Double scale) {
        // 获取缩放后的长宽
        int scaledWidth = (int) (sourceBufferImage.getWidth() * scale);
        int scaledHeight = (int) (sourceBufferImage.getHeight() * scale);

        // 获取缩放后的image
        Image scaledImage = sourceBufferImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        // 创建scaled BufferImage
        BufferedImage scaledBufferImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);

        // 创建图层
        Graphics2D g2d = scaledBufferImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        // 结束
        g2d.dispose();
        return scaledBufferImage;
    }
}
