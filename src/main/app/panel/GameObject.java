package main.app.panel;


import java.awt.*;

/**
 * @author wuzf
 * @create 2020/9/18
 * @功能描述 游戏中的物体
 */
public interface GameObject {
    Rectangle getRectangle();//定位
    void destroy();//销毁
    void draw(Graphics g);//渲染
}
