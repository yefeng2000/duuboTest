package com.example.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * MD5方式比较图片是否重复
 */
public class ImgRepeatUtil {


    //通过图片本地路径拿到MD5
    public static String getMD5(String imagePath) throws Exception {
        InputStream in = new FileInputStream(new File(imagePath));
        StringBuffer md5 = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] dataBytes = new byte[1024];
        int nread = 0;
        while ((nread = in.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        byte[] mdbytes = md.digest();
        // convert the byte to hex format
        for (int i = 0; i < mdbytes.length; i++) {
            md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        in.close();
        return md5.toString().toLowerCase();
    }

    /**
     * 验证证还是重复
     * @param photo 判重文件
     * @param realPath	文件上传目录(需要与目录中进行比较)
     * @return
     * @throws Exception
     */
    public static String verification(String photo,String realPath, String extension)throws Exception {
        String message = "";
        String photoMd5 = getMD5(photo);
        File file = new File(realPath);
        String[] filelist = file.list();
        for (int j = 0; j < filelist.length; j++) {
            File readfile = new File(realPath + "\\" + filelist[j]);
            if (!readfile.isDirectory()) {
                String absolutepath = readfile.getAbsolutePath();
                String photoFileMd5 = getMD5(absolutepath);
                if (photoFileMd5.equals(photoMd5)) {
                    message +=  absolutepath.substring((absolutepath.lastIndexOf("\\") + 1), absolutepath.length())
                            + ",";
                }
            }
        }
        if(message.length()>0){
            message = photo  + " 与 " + message+" 文件内容一样！";
        }
        System.out.println("=======结果："+message);
        if (!"".equals(message)) {
            return message;
        }

        return message;
    }

    public static void main(String[] args) throws Exception {
        verification("E:\\test\\test.jpg","E:\\test\\testimg","");
    }
}
