package com.example.demo.util;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class WaterMarkUtils {
    /**
     * 图片添加水印
     *
     * @param srcImgPath       需要添加水印的图片的路径
     * @param outImgPath       添加水印后图片输出路径
     * @param markContentColor 水印文字的颜色
     * @param waterMarkContent 水印的文字
     */
    public static void mark(String srcImgPath, String outImgPath, Color markContentColor, String waterMarkContent) {
        FileOutputStream outImgStream = null;
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
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
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
        } finally {
            if (outImgStream != null) {
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
     *
     * @param waterMarkContent 水印的文字
     * @param g
     * @return 水印文字总长度
     */
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }


    public static void test() {
        System.out.println("开始水印：");
        addWatermark("f:/123.jpg", "f:/456.jpg", "你好，世界！", "jpg");
        System.out.println("水印完成。");
    }

    /**
     * @param fileExt 图片格式     *
     * @return void
     * @description * @param sourceImgPath 源图片路径
     * * @param tarImgPath 保存的图片路径
     * * @param waterMarkContent 水印内容     *
     */
    public static void addWatermark(String sourceImgPath, String tarImgPath, String waterMarkContent, String fileExt) {
        Font font = new Font("宋体", Font.BOLD, 36);
        //水印字体，大小
        Color markContentColor = Color.red;
        //水印颜色
        Integer degree = 45;
        //设置水印文字的旋转角度
        float alpha = 0.5f;
        //设置水印透明度
        OutputStream outImgStream = null;
        try {
            File srcImgFile = new File(sourceImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();//得到画笔
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(markContentColor); //设置水印颜色
            g.setFont(font);              //设置字体
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));//设置水印文字透明度
            if (null != degree) {
                g.rotate(Math.toRadians(degree));//设置水印旋转
            }
            JLabel label = new JLabel(waterMarkContent);
            FontMetrics metrics = label.getFontMetrics(font);
            int width = metrics.stringWidth(label.getText());//文字水印的宽
            int rowsNumber = srcImgHeight / width;// 图片的高  除以  文字水印的宽    ——> 打印的行数(以文字水印的宽为间隔)
            int columnsNumber = srcImgWidth / width;//图片的宽 除以 文字水印的宽   ——> 每行打印的列数(以文字水印的宽为间隔)
            // 防止图片太小而文字水印太长，所以至少打印一次
            if (rowsNumber < 1) {
                rowsNumber = 1;
            }
            if (columnsNumber < 1) {
                columnsNumber = 1;
            }
            for (int j = 0; j < rowsNumber; j++) {
                for (int i = 0; i < columnsNumber; i++) {
                    g.drawString(waterMarkContent, i * width + j * width, -i * width + j * width);//画出水印,并设置水印位置

                }
            }
            g.dispose();// 释放资源
            // 输出图片
            outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, fileExt, outImgStream);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            try {
                if (outImgStream != null) {
                    outImgStream.flush();
                    outImgStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
        }
    }


    public static void main(String[] args) {
        // 原图位置, 输出图片位置, 水印文字颜色, 水印文字
        mark("E:/微信截图_20190627112122.png", "E:/水印test.png", Color.RED, "水印效果测试");
        addWatermark("E:/微信截图_20190627112122.png", "E:/水印test2.png", "水印效果测试", "png");
    }
}
