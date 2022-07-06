package admin;

import org.apache.commons.lang.RandomStringUtils;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author quavario@gmail.com
 * @date 2021/5/24 17:30
 */
public class Test2 {
    public static void main(String[] args) {
        String icon = "E:\\Dev\\git_repository\\msm\\msm-admin\\src\\main\\resources\\image\\logo3.png";
//        String source = "C:\\Users\\quam\\Pictures\\test\\f2060a01-aca3-48c0-a130-feab959a55d4.jpg";
        String source = "C:\\Users\\quam\\Pictures\\wallpaper\\苹果iMac Pro Original 8k壁纸_彼岸图网.jpg";
        String output = "C:\\Users\\quam\\Desktop";
        String filename= RandomStringUtils.randomAlphanumeric(5);
        String imageName = filename;
        String imageType = "jpg";
        Integer degree = null;
        markImageBySingleIcon(icon, source, output, imageName, imageType, degree);
    }

    /**
     *icon 水印图片路径（如：F:/images/icon.png）
     *source 没有加水印的图片路径（如：F:/images/6.jpg）
     *output 加水印后的图片路径（如：F:/images/）
     *imageName 图片名称（如：11111）
     *imageType 图片类型（如：jpg）
     *degree 水印图片旋转角度，为null表示不旋转
     */
    public static Boolean markImageBySingleIcon(String icon,String source,String output,String imageName,String imageType,Integer degree) {

        try {

            File sourceFile = new File(source);
            File logoFile = new File(icon);

            if (!sourceFile.isFile()) {
                return false;
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

            if (null != degree) {
                //设置水印旋转
                g.rotate(Math.toRadians(degree),(double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
            }
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
            File sf = new File(output, imageName+"."+imageType);
            ImageIO.write(bi, imageType, sf); // 保存图片

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
