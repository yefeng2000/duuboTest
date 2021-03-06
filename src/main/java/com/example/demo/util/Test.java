package com.example.demo.util;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wangkai
 */
public class Test {
    /**
     *
     */
    public Test() {
        try {
            //生成以后新的图片地址
            File fo = new File("E:\\4.jpg");
            //读取的图片文件
            String imagePath = "E:\\1.jpeg";
            //盖章的图片文件
            String toimagepth = "E:\\2.jpeg";
            //得到图片的文件流
            InputStream imageIn;
            imageIn = new FileInputStream(new File(imagePath));
            //得到输入的编码器，将文件流进行jpg格式编码
            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageIn);
            //得到编码后的图片对象
            BufferedImage image = decoder.decodeAsBufferedImage();
            Graphics g = image.getGraphics();
            try {
                InputStream imageIn2 = null;
                imageIn2 = new FileInputStream(new File(toimagepth));
                //得到输入的编码器，将文件流进行jpg格式编码
                JPEGImageDecoder decoder2 = JPEGCodec
                        .createJPEGDecoder(imageIn2);
                //得到编码后的图片对象
                BufferedImage image2 = decoder2.decodeAsBufferedImage();
                //加盖图片章
                ImageObserver obser = null;
                int x = image.getWidth() - image2.getWidth();
                int y = image.getHeight() - image2.getHeight();
                g.drawImage(image2, x, y, obser);
            } catch (FileNotFoundException e) {
                //打开文件失败，表示章图片不存在，这时候直接加盖文件章（签名）
                g.setFont(new Font("宋体", Font.PLAIN, 18));
                g.drawString("秋水工作室", image.getWidth() - 100,
                        image.getHeight() - 20);
                g.drawString("water_wang@xs.zj.cn", image.getWidth() - 180,
                        image.getHeight() - 10);
            }
            g.dispose();
            ImageIO.write(image, "jpeg", fo);
            System.out.println("ok");
        } catch (FileNotFoundException e) {
            // 自动生成 catch 块
            e.printStackTrace();
        } catch (ImageFormatException e) {
            // 自动生成 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // 自动生成 catch 块
            e.printStackTrace();
        }
    }
    public static void saveFixedBoundIcon(File imageFile, int height, int width)
            throws Exception {
        double Ratio = 0.0;
        if (imageFile == null || !imageFile.isFile())
            throw new Exception(imageFile + "找不到指定的文件!");
        String filePath = imageFile.getPath();
        BufferedImage Bi = ImageIO.read(imageFile);
        height = Bi.getHeight();
        width = Bi.getWidth();
        if ((Bi.getHeight() >= height) || (Bi.getWidth() >= width)) {
            if (Bi.getHeight() > Bi.getWidth()) {
                Ratio = (new Integer(height)).doubleValue() / Bi.getHeight();
            } else {
                Ratio = (new Integer(width)).doubleValue() / Bi.getWidth();
            }
            File savefile = new File(filePath + "_" + height + "_" + width
                    + ".jpg");
            Image Itemp = Bi.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            AffineTransformOp op = new AffineTransformOp(AffineTransform
                    .getScaleInstance(Ratio, Ratio), null);
            Itemp = op.filter(Bi, null);
            try {
                ImageIO.write((BufferedImage) Itemp, "jpeg", savefile);
            } catch (Exception ex) {
            }
        }
    }
    public static void main(String[] args) {
        //       Test ts = new Test();
        try {
            new Test();
            Test.saveFixedBoundIcon(new File(
                    "E:\\test.jpg"), 900, 900);
        } catch (Exception e) {
            // 自动生成 catch 块
            e.printStackTrace();
        }
    }
}
