package main.app.panel;

import main.app.PanelGame;
import main.util.Util;

import java.awt.*;

/**
 * @author wuzf
 * @create 2020/9/18
 * @功能描述 爆炸
 */
public class Explode {
    private int x,y;//爆炸位置
    private static Image[] images = new Image[10];
    private int count = 0;//当前播放到的图片在数组中的下标
    private PanelGame game;

    //静态加载图片
    static {
        for (int i = 0; i < images.length; i++) {
            images[i] = Util.loadImage("/lib/panel/explode/" + i + ".gif ");
            //避免JAVA的懒加载，让他一用到就可以直接显示，否则可能一开始的两站图看不到
            images[i].getHeight(null);
        }
    }

    public Explode(int x, int y, PanelGame game) {
        this.x = x;
        this.y = y;
        game.getExplodes().add(this);
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        if(count == 3){
            //播放完后将自己从game中去掉
            game.getExplodes().remove(this);
        }
        g.drawImage(images[count ++], this.x - images[count ++].getWidth(null)/2 , this.y - images[count ++].getHeight(null)/2, 30, 30,  null);
        g.setColor(c);
    }
}
