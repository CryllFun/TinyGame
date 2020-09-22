package main.app.panel;

import main.app.PanelGame;
import main.util.Util;

import java.awt.*;
import java.util.List;

/**
 * @author wuzf
 * @create 2020/9/18
 * @功能描述 炮弹
 */
public class Shell implements GameObject{
    private int x,y;
    private static final int WIDTH=10,HEIGHT=10;
    private boolean isLive = true;//默认炮弹存活
    private Panel panel;
    private int step = 5;


    public Shell(int x, int y, Panel panel) {
        this.x = x;
        this.y = y;
        this.panel = panel;
    }
    @Override
    public void draw(Graphics g){
        if(this.isLive){
            Color c = g.getColor();
            if(panel.isEnemy()){
                g.setColor(Color.red);
            }else {
                g.setColor(Color.yellow);
            }
            g.fillOval(this.x, this.y, WIDTH, HEIGHT);
            g.setColor(c);
            move();
            this.hit();
        }
    }
    //击中
    private void hit() {
        try {
            PanelGame game = panel.getGame();
            List<Panel> enemyPanels = game.getPanels();
            for (Panel panel1 :enemyPanels) {
                if(panel.isEnemy() == panel1.isEnemy()){
                    continue;
                }
                //击中后，发生爆炸，炮弹销毁，飞机销毁
                if(Util.isHit(this,panel1)){
                    this.destroy();
                    panel1.destroy();
                    game.getExplodes().add(new Explode(this.x,this.y,game));
                }
            }
        } catch (Exception e) {

        }
    }
    //子弹移动
    private void move() {
        if(panel.isEnemy()){
            x+=step;
        }else {
            x-=step;
        }
        if(x<10||x>panel.getGame().getWidth()){
            this.destroy();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }


    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }
    @Override
    public void destroy(){
        this.setLive(false);
        panel.getShells().remove(this);
    }
}
