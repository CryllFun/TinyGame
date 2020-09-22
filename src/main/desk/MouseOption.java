package main.desk;

import main.app.PanelGame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author wuzf
 * @create 2020/9/16
 * @功能描述
 */
public class MouseOption extends MouseAdapter {
    private int count;
    private Timer timer = new Timer();
    private String str = null;
    private Desktop parent;

    public MouseOption(Desktop desktop){
        this.parent = desktop;
    }
    @Override
    public void mouseClicked(MouseEvent event) {
        JButton button = (JButton) event.getSource();
        button.setContentAreaFilled(true);
        str = button.getText();
        //记录点击次数
        count++;
        //定制双击事件,使用定时器，每次点击后，延时0.4
        timer.schedule(new MyTimerTask(button),400);
    }
    private class MyTimerTask extends TimerTask {
        JButton button = null;

        public MyTimerTask(JButton button){
            this.button=button;
        }

        @Override
        public void run() {
            //超过0.4s且点击了一次
            if(count == 1){
                count = 0;
                return;
            }
            if(count!=2){
                return;
            }
            //在0.4s内点击两次
            switch(str) {
                case "fileChooser" :
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    fileChooser.showOpenDialog(parent);
                    File file = fileChooser.getSelectedFile();
                    if(file != null) {

                        System.out.println(file.getAbsolutePath());

                    }
                    break;
                case "notebook" :
//                    调用windows系统自带的notepad
                        try {
                            Runtime.getRuntime().exec("notepad");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    //调用自个写的一个特简易的记事本程序
                    System.out.println("启动记事本");
//                    Notepad notepad = new Notepad();
//                    parent.getDesktop().add(notepad);
//                    notepad.toFront();

                    break;
                case "compute" :
                    System.out.println("打开windows的文件管理器");
                    try {
                        java.awt.Desktop.getDesktop().open(new File(System.getProperty("user.home")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "panel" :
                    System.out.println("启动飞机大战游戏");
                    new PanelGame(900,600);
                    break;

                case "sunModel" :
                    System.out.println("启动太阳系模型");
//                    new SunModel();
                    break;
            }

            button.setContentAreaFilled(false);
            count = 0;
        }

    }

}


