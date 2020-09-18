package main.app;

import main.app.panel.Explode;
import main.app.panel.Panel;
import main.app.panel.Shell;
import main.util.Util;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author wuzf
 * @create 2020/9/17
 * @功能描述 飞机大战
 */
public class PanelGame extends Frame implements Serializable {
    //飞机场
    private List<Panel> panels = new ArrayList<Panel>();
    //公共的子弹工厂
    private List<Shell> shells = new ArrayList<Shell>();
    //随机
    private Random random = new Random();
    private int width = 500;
    private int height = 500;
    //战场背景图
//    private static Image image = Util.loadImage("");
    //缓冲图
//    private BufferedImage buffImage = new Bu
    //爆炸
    private List<Explode> explodes = new ArrayList<Explode>();
    //杀敌数
    private int killEnemyCount = 0;
    //死亡次数
    private int deadCount = 0;
    public PanelGame(int width,int height){
        this.setSize(width,height);
        this.setLocation(300,100);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PanelGame.this.dispose();
            }
        });
        this.addKeyListener(new keyCtry());
        setSelfPanels(1);

    }

    public List<Panel> getEnemyPanels() {
        return enemyPanels;
    }

    public void setEnemyPanels(List<Panel> enemyPanels) {
        this.enemyPanels = enemyPanels;
    }

    public List<Panel> getSelfPanels() {
        return selfPanels;
    }

    public void setSelfPanels(int count) {
        for (int i = 0; i < count; i++) {
            selfPanels.add(new Panel(this,true));
        }
    }

    public List<Shell> getShells() {
        return shells;
    }

    public void setShells(List<Shell> shells) {
        this.shells = shells;
    }


    public List<Panel> getPanels() {
        return panels;
    }

    public void setPanels(List<Panel> panels) {
        this.panels = panels;
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
}
