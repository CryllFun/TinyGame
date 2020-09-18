package main.util;

import main.app.panel.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author wuzf
 * @create 2020/9/17
 * @功能描述
 */
public class Util {
    public static Image loadImage(String path){

        BufferedImage image = null;

        try {
            image = ImageIO.read(Util.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    //判断碰撞
    public static boolean isHit(GameObject go1,GameObject go2){
        Rectangle reg1 = go1.getRectangle();
        Rectangle reg2 = go2.getRectangle();
        return reg1.intersects(reg2);
    }
}
