package main.app.panel;

import main.app.PanelGame;
import main.util.Util;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author wuzf
 * @create 2020/9/18
 * @功能描述 飞机
 */
public class Panel implements GameObject{
    private int x , y, oldx = x, oldy = y;;//飞机位置
    private static final int WIDTH = 48,HEIGHT = 48;//飞机的大小
    private boolean up = false,right = false,down = false,left = false;//飞机移动方向
    private boolean enemy = true;//默认为敌机
    private static Image enemyImg = Util.loadImage("");//敌机图片
    private static Image selfImg = Util.loadImage("");//友机图片
    private List<Shell> shells = null;//子弹容器
    private int step = 1;//移动的步数
    private boolean isLive = true;//默认飞机生存
    private int keep = 0;//敌机自动发射子弹的间隔
    private Random random = new Random();//用于定义敌机的子弹发射的时间间隔
    private Director dir = Director.STOP;//飞机默认是不动的
    private PanelGame game;//游戏主类
    private int lifeValue=100;//生命值


    public Panel(PanelGame game,boolean enemy) {
        this.game = game;
        this.enemy = enemy;
        //如果是敌机，定义它的初始位置
        if(enemy){
            this.x = -random.nextInt(3*WIDTH);
            this.y = random.nextInt(game.getHeight() - 2*HEIGHT);
        }
        this.shells = game.getShells();//无限子弹
    }

    public Panel(int x, int y, boolean enemy, PanelGame game) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.game = game;
        this.shells = game.getShells();
    }

    @Override
    public void draw(Graphics g) {
        if(!this.isLive){
            return;
        }
        Color c = g.getColor();
        Image image = this.selfImg;
        if(this.enemy){
            image = enemyImg;
        }
        g.drawImage(image,x,y,WIDTH,HEIGHT,null);
        g.setColor(c);
        this.move();
        this.hit();
    }
    //通过监听按键来确定方向

    //移动
    //发射子弹
    public void launch(){
        int xy1 = x-10;
        int xy2 = x+20;
        if(enemy){
            xy1 = x+50;
            xy2 = x+20;
        }
        Shell shell = new Shell(xy1,xy2,this);
        this.getShells().add(shell);
    }
    //击中
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(x,y,WIDTH, HEIGHT);
    }

    @Override
    public void destroy() {
        this.isLive = false;
        if(this.isEnemy()){
            game.setKillEnemyCount();
        }else{
            this.lifeValue -=10;
            if(this.lifeValue<=0){
                game.setDeadCount();
            }
        }
        game.getPanels().remove(this);
    }

    public PanelGame getGame() {
        return game;
    }

    public void setGame(PanelGame game) {
        this.game = game;
    }

    public List getShells() {
        return shells;
    }

    public boolean isEnemy() {
        return enemy;
    }
}
