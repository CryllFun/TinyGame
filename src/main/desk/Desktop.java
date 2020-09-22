package main.desk;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author wuzf
 * @create 2020/9/16
 * @功能描述
 */
public class Desktop extends JFrame implements Serializable {
    private JDesktopPane desktop = null;//桌面面板
    private String bgPath = "/lib/background.jpg";//桌面背景
    private MouseOption mouseOption = new MouseOption(Desktop.this);//鼠标监听对象
    private static int width = 1024;
    private static int height = 719;

    public Desktop(String title){
        super(title);
        Toolkit tk = Toolkit.getDefaultToolkit();
        //获取屏幕大小
        Dimension dm = tk.getScreenSize();
        //设置布局管理为BorderLayout
        this.setLayout(new BorderLayout());
        this.setSize(width, height);

        desktop = new JDesktopPane();
        System.out.println("成功启动桌面！");
        setBackground(width,height,bgPath);

        setAppButton("notebook","/lib/notebook.jpg",new int[]{20,88,48,38});

        setAppButton("panel","/lib/panel.jpg",new int[]{20,156,48,48});

        setAppButton("sunModel","/lib/sunModel.jpg",new int[]{20,224,48,36});

        System.out.println("成功加载桌面程序！");
        this.getContentPane().add(desktop,BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
    public void setAppButton(String appName,String imgUrl,int[] xy){
        JButton button = new JButton();
        button.setIcon(new ImageIcon(this.getClass().getResource(imgUrl)));
        button.setBounds(xy[0],xy[1],xy[2],xy[3]);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addMouseListener(mouseOption);
        button.setText(appName);
        desktop.add(button,Integer.MIN_VALUE+1);
    }
    public void setDesktop(JDesktopPane desktop) {
        this.desktop = desktop;
    }
    public void setBackground(int width,int height,String bgImgUrl) {
        JLabel background = new JLabel();
        //创建一个空的图片
        BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        BufferedImage ad = null;
        try{
            //读取背景图片
            ad = ImageIO.read(this.getClass().getResource(bgImgUrl));
        }catch (IOException e){
            e.printStackTrace();
        }
        //将背景图按比例缩放重新画到之间创建的空图片
        g.drawImage(ad,0,0,width,height,null);
        //转化为Icon类图片
        ImageIcon ii = new ImageIcon(img);
        background.setIcon(ii);
        //设置存放背景图的背景标签的位置和大小
        background.setBounds(new Rectangle(0,0,width,height));
        desktop.add(background,new Integer(Integer.MIN_VALUE));
    }
    public  int getWidth() {
        return width;
    }
    public static void setWidth(int width) {
        Desktop.width = width;
    }
    public  int getHeight() {
        return height;
    }
    public static void setHeight(int height) {
        Desktop.height = height;
    }
    public JDesktopPane getDesktop() {
        return this.desktop;
    }
}
