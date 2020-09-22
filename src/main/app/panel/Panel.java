package main.app.panel;

import main.app.PanelGame;
import main.util.Util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

/**
 * @author wuzf
 * @create 2020/9/18
 * @功能描述 飞机
 */
public class Panel implements GameObject{
    private int x = 452 , y = 250, oldx = x, oldy = y;;//飞机位置
    private static final int WIDTH = 48,HEIGHT = 48;//飞机的大小
    private boolean up = false,right = false,down = false,left = false;//飞机移动方向
    private boolean enemy = true;//默认为敌机
    private static Image enemyImg = Util.loadImage("/lib/panel/enemy.jpg");//敌机图片
    private static Image selfImg = Util.loadImage("/lib/panel/self.jpg");//友机图片
    private List<Shell> shells = null;//子弹容器
    private int step = 3;//移动的步数
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
        this.shells = game.getShells();
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
    //碰撞
    public void hit(){
        try {
            List<Panel> panels = game.getPanels();
            for (Panel p :panels) {
                if(this.enemy==p.enemy){
                    continue;
                }
                if(Util.isHit(this,p)){
                    this.destroy();
                    p.destroy();
                    game.getExplodes().add(new Explode(this.x,this.y,game));
                }
            }
        } catch (Exception e) {

        }
    }
    //移动
    public void move(){
        oldx = x;
        oldy = y;
        if(enemy){
            step = 1;
        }
        switch (dir){
            case UP:
                y-=step;
                break;
            case RIGHT:
                x+=step;
                break;
            case DOWN:
                y+=step;
                break;
            case LEFT:
                x-=step;
                break;
            case LEFT_UP:
                x-=step;
                y-=step;
                break;
            case RIGHT_UP:
                x+=step;
                y-=step;
                break;
            case LEFT_DOWN:
                x-=step;
                y+=step;
                break;
            case RIGHT_DOWN:
                x+=step;
                y+=step;
                break;
            default:
                break;
        }
        if(enemy){
            dir = Director.RIGHT;
            //每隔50个刷新周期，发射一枚子弹
            if(keep == 0){
                keep = 50;
                this.createShells();
            }
            keep--;
            //如果敌机逃出战场，摧毁它
            if(x>game.getWidth()){
                game.getPanels().remove(this);
            }
        }else{
            if(x>game.getWidth()-48 || x<0){
                x = oldx;
            }
            if(y>game.getHeight()-48 || y<30){
                y = oldy;
            }
        }
    }
    //键盘抬起监听事件
    public void keyReleased(KeyEvent e){
        if(enemy){
            return;
        }
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                this.up = false;
                break;
            case KeyEvent.VK_RIGHT:
                this.right = false;
                break;
            case KeyEvent.VK_DOWN:
                this.down = false;
                break;
            case KeyEvent.VK_LEFT:
                this.left = false;
                break;
        }
        this.directer();
    }
    //键盘按下监听事件
    public void keyPressed(KeyEvent e){
        if(enemy){
            return;
        }
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                this.up = true;
                break;
            case KeyEvent.VK_RIGHT:
                this.right = true;
                break;
            case KeyEvent.VK_DOWN:
                this.down = true;
                break;
            case KeyEvent.VK_LEFT:
                this.left = true;
                break;
            case KeyEvent.VK_CONTROL:
                this.createShells();
        }
        this.directer();
    }
    //通过监听按键来确定方向
    private void directer(){
        boolean isLeft = left && !up && !right && !down;
        boolean isUp = !left && up && !right && !down;
        boolean isRight = !left && !up && right && !down;
        boolean isDown = !left && !up && !right && down;
        boolean isUpRight = !left && up && right && !down;
        boolean isUpLeft = left && up && !right && !down;
        boolean isDownRight = !left && !up && right && down;
        boolean isDownLeft = left && !up && !right && down;
        if(isLeft){
            dir = Director.LEFT;
        }else if(isUp){
            dir = Director.UP;
        }else if(isRight){
            dir = Director.RIGHT;
        }else if(isDown){
            dir = Director.DOWN;
        }else if(isUpRight){
            dir = Director.RIGHT_UP;
        }else if(isUpLeft){
            dir = Director.LEFT_UP;
        }else if(isDownRight){
            dir = Director.RIGHT_DOWN;
        }else if(isDownLeft){
            dir = Director.LEFT_DOWN;
        }else {
            dir = Director.STOP;
        }
    }
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
        if(this.isEnemy()){
            this.isLive = false;
            game.setKillEnemyCount();
            game.getPanels().remove(this);

        }else{
            this.lifeValue -=10;
            System.out.println("被击中，注意躲闪 ！");
            if(this.lifeValue<=0){
                System.out.println("你没了！");
                this.isLive = false;
                game.setDeadCount();
                game.getPanels().remove(this);
                //复活
                game.addPanels(false,1);
            }
        }
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
    public void createShells() {

        if(enemy){
            this.getShells().add(new Shell(x+50,y+20,this));
        }else {
            this.getShells().add(new Shell(x-10,y+20,this));
        }
    }

    public int getLifeValue() {
        return lifeValue;
    }
}
