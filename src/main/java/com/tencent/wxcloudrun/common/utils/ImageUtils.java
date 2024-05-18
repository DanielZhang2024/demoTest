package com.tencent.wxcloudrun.common.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUtils {
    /**
     * 下载网络图片
     * @param imageUrl
     * @return
     */
    public static BufferedImage getImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为240秒
            conn.setConnectTimeout(240 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(getData));
            return image;
        }catch(Exception e) {
            return null;
        }
    }

    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 二维码设置自定义的边框
     * @param image
     * @return
     */
    public static BufferedImage builderQrCodeImage(BufferedImage image) {
        try {
            File file = new File("d:\\789.jpg");
            BufferedImage borderImage = ImageIO.read(new File(file.getPath()));
            BufferedImage weiXinImage = new BufferedImage(image.getWidth() + 100, image.getHeight() + 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = weiXinImage.createGraphics();
            //消除文字锯齿
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            //消除图片锯齿
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //放入二维码
            graphics.drawImage(image, 50, 50, image.getWidth(), image.getHeight(), null);
            //放入边框
            graphics.drawImage(borderImage, 0, 0, image.getWidth() + 100, image.getHeight() + 100, null);
            return weiXinImage;
        } catch (IOException e) {
            return image;
        }
    }

    /**
     * 将BufferedImage转为base64
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    public static String bufferImageToBase64(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", stream);
        // 对字节数组Base64编码
        Base64 base = new Base64();
        String base64 = base.encodeToString(stream.toByteArray());
        return "data:image/png;base64," + base64;

    }

    public static byte[] bufferImageToByteArray(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", stream);
        return stream.toByteArray();
    }
}
