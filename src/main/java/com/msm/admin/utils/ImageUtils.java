package com.msm.admin.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author quavario@gmail.com
 * @date 2021/5/25 18:01
 */
public class ImageUtils {
    private static String logo = "/image/logo3.png";

    public static void main(String[] args) throws FileNotFoundException {
        String file = ImageUtils.class.getResource(logo).getPath();
        File file1 = new File(file);
        System.out.println(file1.exists());

    }
    public static BufferedImage addWaterMark(String source) {
        try {
            File logoFile = new File(ImageUtils.class.getClassLoader().getResource(logo).getPath());
            File sourceFile = new File(source);

            if (!sourceFile.isFile()) {
                throw new RuntimeException("找不到文件");
            }

            //将icon加载到内存中
            BufferedImage logoBufferImage = ImageIO.read(logoFile);
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
            int scalePercent = 20;

            double scaledHeight = sourceImageHeight * 0.2;
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

            return bi;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    public static String getContent(String url){

        Element doc = Jsoup.parseBodyFragment(url).body();
        Elements pngs = doc.select("img[src]");
        String httpHost = "http://47.92.231.181:9002";
        for (Element element : pngs) {
            String imgUrl = element.attr("src");
            if (imgUrl.trim().startsWith("/")) { // 会去匹配我们富文本的图片的 src 的相对路径的首个字符，请注意一下
                imgUrl =httpHost + imgUrl;
                element.attr("src", imgUrl);
            }
        }
        url = doc.toString();
        return url;
    }
}
