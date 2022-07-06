package admin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileDemo {

    public static void main(String[] args) {
        try {
            BufferedImage prevImage =ImageIO.read(new File("D:/logo3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
