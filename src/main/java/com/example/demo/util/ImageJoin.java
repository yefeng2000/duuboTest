package com.example.demo.util;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * 用于两个图片拼接成一个图片
 */
public class ImageJoin {



    public static void mergeImage(String fileStr1, String fileStr2, String fileStr3, String path)

            throws IOException {

        File file1 = new File(path, fileStr1);

        File file2 = new File(path, fileStr2);



        // BufferedImage image1 = ImageIO.read(file1);

        // BufferedImage image2 = ImageIO.read(file2);



        BufferedImage image1 = ImgCompress(file1);

        BufferedImage image2 = ImgCompress(file2);



        BufferedImage combined = new BufferedImage(image1.getWidth(), image1.getHeight() * 2,

                BufferedImage.TYPE_INT_RGB);



        // paint both images, preserving the alpha channels

        Graphics g = combined.getGraphics();

        try {

            g.drawImage(image1, 0, 0, null);

            g.drawImage(image2, 0, image1.getHeight(), null);



            // Save as new image

            ImageIO.write(combined, "JPG", new File(path, fileStr3));

        } finally {

            if (g != null) {

                g.dispose();

            }

        }

    }



    // 图片压缩处理



    private static Image img;

    private static int width;

    private static int height;



    public static BufferedImage ImgCompress(File file) throws IOException {

        img = ImageIO.read(file); // 构造Image对象

        width = img.getWidth(null); // 得到源图宽

        height = img.getHeight(null); // 得到源图长



//    return resizeFix(400, 492);

        return resizeFix(600, 692);

    }



    /**

     * 按照宽度还是高度进行压缩

     *

     * @param w

     *          int 最大宽度

     * @param h

     *          int 最大高度

     */

    public static BufferedImage resizeFix(int w, int h) throws IOException {

        if (width / height > w / h) {

            return resizeByWidth(w);

        } else {

            return resizeByHeight(h);

        }

    }



    /**

     * 以宽度为基准，等比例放缩图片

     *

     * @param w

     *          int 新宽度

     */

    public static BufferedImage resizeByWidth(int w) throws IOException {

        int h = (int) (height * w / width);

        return resize(w, h);

    }



    /**

     * 以高度为基准，等比例缩放图片

     *

     * @param h

     *          int 新高度

     */

    public static BufferedImage resizeByHeight(int h) throws IOException {

        int w = (int) (width * h / height);

        return resize(w, h);

    }



    /**

     * 强制压缩/放大图片到固定的大小

     *

     * @param w

     *          int 新宽度

     * @param h

     *          int 新高度

     */

    public static BufferedImage resize(int w, int h) throws IOException {

        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        try {

            g.drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图

        } finally {

            if (g != null) {

                g.dispose();

            }

        }

        return image;

        // File destFile = new File("C:\\temp\\456.jpg");

        // FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流

        // // 可以正常实现bmp、png、gif转jpg

        // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

        // encoder.encode(image); // JPEG编码

        // out.close();

    }



    public static void main(String[] args) throws IOException {

        String path = "E:\\img\\";

        String fileStr1 = "1.jpg";

        String fileStr2 = "test.jpg";

        String fileStr3 = "new.jpg";

        mergeImage(fileStr1, fileStr2, fileStr3, path);

    }



}
