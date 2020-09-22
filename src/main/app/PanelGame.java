package main.app;

import main.app.panel.Explode;
import main.app.panel.Panel;
import main.app.panel.Shell;
import main.util.Util;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author wuzf
 * @create 2020/9/17
 * @功能描述 飞机大战
 */
public class PanelGame extends Frame implements Serializable {
    //飞机
    private List<Panel> panels = new ArrayList<Panel>();
    //子弹
    private List<Shell> shells = new ArrayList<Shell>();
    //随机
    private Random random = new Random();
    private static int width = 900;
    private static int height = 600;
    //战场背景图
    private static String imagePath = "/lib/panel/pgbg.jpg";
    private static Image image = Util.loadImage(imagePath);
    private static BufferedImage buffImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    //爆炸
    private List<Explode> explodes = new ArrayList<Explode>();
    //杀敌数
    private int killEnemyCount = 0;
    //死亡次数
    private int deadCount = 0;
    public PanelGame(int width,int height){
        this.setSize(width,height);
        this.setLocation(500,100);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PanelGame.this.dispose();
            }
        });
        this.addKeyListener(new keyCtrl());//按键控制
        this.addPanels(false,1);
        this.setVisible(true);
        Thread thread = new Thread(new MyThread());
        thread.start();
    }
    //游戏界面渲染
    @Override
    public void paint(Graphics g) {
        g.drawImage(buffImage,0,0,null);
    }
    //游戏界面刷新，动画效果
    @Override
    public void update(Graphics g){
        try {
            Graphics gameG = buffImage.getGraphics();
            gameG.fillRect(0,0,width,height);
            gameG.drawImage(image,0,0,width,height,null);
            Color c = gameG.getColor();
            gameG.setColor(Color.gray);
            gameG.setFont(new Font("微软雅黑",Font.PLAIN,18));
            gameG.drawString("击毁敌机："+killEnemyCount,10,50);
            gameG.drawString("死亡次数："+deadCount,10,100);
            for (Panel p :panels) {
                if(!p.isEnemy()){
                    p.draw(gameG);
                    gameG.drawString("生命值："+p.getLifeValue(),10,150);
                }
            }
            gameG.setColor(c);
            for (Panel p :panels) {
                if(p.isEnemy()){
                    p.draw(gameG);
                }
            }
            for (Shell shell :shells) {//渲染子弹
                shell.draw(gameG);
            }
            for (Explode explode: explodes) {//渲染爆炸
                explode.draw(gameG);
            }
            paint(g);
            gameG.setColor(c);
            if(this.getEnemyCount()<3){//敌机小于三架时，生成新的敌机
                this.addPanels(true,random.nextInt(6)+1);
            }
        } catch (Exception e) {
        }
    }

    public List<Shell> getShells() {
        return shells;
    }
    public int getEnemyCount(){
        int count = 0;
        for (Panel p: panels){
            if(p.isEnemy()){
                count++;
            }
        }
        return count;
    }
    public void setShells(List<Shell> shells) {
        this.shells = shells;
    }


    public List<Panel> getPanels() {
        return panels;
    }

    public void addPanels(boolean enemy,int count) {
        boolean exist = false;
        if(!enemy){
            for (Panel p:panels) {
                if(!p.isEnemy()){
                    exist = true;
                    break;
                }
            }
        }
        //我方战机只能有一架
        if (exist){
            return;
        }
        for (int i = 0; i < count; i++) {
            this.panels.add(new Panel(this,enemy));
        }
    }

    public List<Explode> getExplodes() {
        return explodes;
    }

    public void setExplodes(List<Explode> explodes) {
        this.explodes = explodes;
    }

    public int getKillEnemyCount() {
        return killEnemyCount;
    }

    public void setKillEnemyCount() {
        this.killEnemyCount ++;
    }

    public int getDeadCount() {
        return deadCount;
    }

    public void setDeadCount() {
        this.deadCount ++;
    }
    //按键监听
    private class keyCtrl extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if(keyCode == KeyEvent.VK_F1) {//f1复活
                addPanels(false,1);

            }

            for(int i = 0; i < panels.size(); i++) {
                panels.get(i).keyPressed(e);
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            for(int i = 0; i < panels.size(); i++) {
                panels.get(i).keyReleased(e);
            }
        }

    }

    private class MyThread implements Runnable {
        @Override
        public void run() {
            while(true) {
                //调用重画方法
                repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


    }
}
