package com.hits.common.util;

import org.nutz.ioc.loader.annotation.IocBean;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Wizzer on 14-5-22.
 */
@IocBean
public class ImagesUtil {
    /**JAVAˮӡͼ�������PNG����͸������
     * @param fileImage ������ͼƬ
     * @param markImage ˮӡͼƬ
     * @param resize    �Ƿ��Զ�����
     * @param location  1�����Ͻǣ�2�����Ͻǣ�3�����½ǣ�4�����½ǣ�5�����м䣬6����ƫ�ϣ�7����ƫ��
     * @return
     */
    public BufferedImage createMark(BufferedImage fileImage, BufferedImage markImage, boolean resize, int location) {
        int x = 0, y = 0;
        int w = markImage.getWidth(), h = markImage.getHeight();
        int nw = w, nh = h;
        ImageIcon imgIcon = new ImageIcon(fileImage);
        Image theImg = imgIcon.getImage();
        int width = theImg.getWidth(null);
        int height = theImg.getHeight(null);
        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //ˮӡͼƬ
        ImageIcon imgMark;
        if (resize) {
            //����ˮӡͼƬ
            if (w > width / 2) {
                nw = (int) ((double) width / 2);
                nh = (int) ((double) (nw * h) / w);
            } else if (nh > height / 2) {
                nh = (int) ((double) height / 2);
                nw = (int) ((double) (nh * w) / h);
            }
            BufferedImage nimage = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_RGB);
            Graphics2D gc = nimage.createGraphics();
            //����ˮӡ͸������ɫ
            nimage = gc.getDeviceConfiguration().createCompatibleImage(nw, nh, Transparency.TRANSLUCENT);
            gc.dispose();
            gc = nimage.createGraphics();
            gc.fillRect(0, 0, w, h);
            gc.setComposite(AlphaComposite.Src);
            gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gc.drawImage(markImage, 0, 0, nw, nh, null, null);
            gc.dispose();
            imgMark = new ImageIcon(nimage);
        } else imgMark = new ImageIcon(markImage);
        Image theImgMark = imgMark.getImage();
        Graphics2D g = bimage.createGraphics();
        g.setBackground(Color.white);
        g.drawImage(theImg, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f)); //���ͼƬˮӡ
        switch (location) {//����ˮӡͼλ��
            case 1:
                break;
            case 2:
                x = width - nw;
                break;
            case 3:
                y = height - nh;
                break;
            case 4:
                x = width - nw;
                y = height - nh;
                break;
            case 5:
                x = (width - nw) / 2;
                y = (height - nh) / 2;
                break;
            case 6:
                x = (width - nw) / 2;
                y = (height - nh) / 2 - (height - nh) / 4;
                break;
            case 7:
                x = (width - nw) / 2;
                y = (height - nh) / 2 + (height - nh) / 4;
                break;
        }
        g.drawImage(theImgMark, x, y, null);
        g.dispose();
        return bimage;
    }
}
