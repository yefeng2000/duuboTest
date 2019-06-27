package com.example.demo.util;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
public class WaterMarkUtils {
    /**
     * 图片添加水印
     * @param srcImgPath 需要添加水印的图片的路径
     * @param outImgPath 添加水印后图片输出路径
     * @param markContentColor 水印文字的颜色
     * @param waterMarkContent 水印的文字
     */
    public static void mark(String srcImgPath, String outImgPath, Color markContentColor, String waterMarkContent) {
        FileOutputStream outImgStream =null;
        try {
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);
            Image srcImg = ImageIO.read(srcImgFile);
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            //Font font = new Font("Courier New", Font.PLAIN, 12);
            Font font = new Font("宋体", Font.PLAIN, 25);
            g.setColor(markContentColor); //根据图片的背景设置水印颜色
            //设置水印透明度
            float alpha = 1f;//值越小颜色越浅
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            g.setFont(font);
            int x = srcImgWidth - getWatermarkLength(waterMarkContent, g) - 3;
            int y = srcImgHeight - 3;
            //int x = (srcImgWidth - getWatermarkLength(waterMarkContent, g)) / 2;
            //int y = srcImgHeight / 2;
            g.drawString(waterMarkContent, x, y);
            g.dispose();
            // 输出图片
            outImgStream = new FileOutputStream(outImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(outImgStream!=null){
                try {
                    outImgStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取水印文字总长度
     * @param waterMarkContent 水印的文字
     * @param g
     * @return 水印文字总长度
     */
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

    public static void main(String[] args) {
        // 原图位置, 输出图片位置, 水印文字颜色, 水印文字
        mark("E:/微信截图_20190627112122.png", "E:/水印test.png", Color.RED, "水印效果测试");
    }
}
