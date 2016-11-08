package com.fun.ui;

import com.fun.utils.DateUtil;
import com.fun.utils.Language;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;

/**
 * Created by yehuan on 2016/11/8.
 *
 */
public class MouseFrame {
    private JTextField hostInput;
    private JTextField portInput;
    private JButton btn;
    private JScrollPane logScrollPane;
    private JPanel mainPanel;
    private JTextPane logPane;
    private JButton btnClear;
    private JLabel host;
    private JLabel port;
    private JLabel logLabel;

    public static void main(String[] args) {

        MouseFrame f = new MouseFrame();
        f.launch();
    }

    public void launch() {

        JFrame jFrame= new JFrame(Language.get("ui.title"));
        Image image;
        try {
            image= ImageIO.read(this.getClass().getResource("/webapp/mouse.png"));
            jFrame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        jFrame.setContentPane(mainPanel);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setSize(800, 600);
        jFrame.setLocationRelativeTo(mainPanel);//居中
        jFrame.setVisible(true);

        MyListener listener = new MyListener();

        btn.setText(Language.get("ui.start"));
        btnClear.setText(Language.get("ui.clear"));
        btn.addActionListener(listener);
        btnClear.addActionListener(listener);

        host.setText(Language.get("ui.host"));
        port.setText(Language.get("ui.port"));
        logLabel.setText(Language.get("ui.log"));
    }

    public class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            JButton button = (JButton)e.getSource();
            if(button == btn){
                String host = hostInput.getText();
                String portStr = portInput.getText();

                host = host == null ? "192.168.1.119":host;
                int port = portStr == null ? 8080:Integer.parseInt(portStr);

                log("click the btn with text [ " +button.getText()+ " ]");
                if(btn.getText().equals(Language.get("ui.start"))){ // 启动
                    log("it't going to start httpserver on [ "+host+" ] with port [ "+port+" ]");
                    // to start server

                    btn.setText(Language.get("ui.stop"));
                }else{// 关闭
                    log("it't going to stop httpserver on [ "+host+" ] with port [ "+port+" ]");
                    // to stop server

                    btn.setText(Language.get("ui.start"));
                }
            }else if(button == btnClear){
                logPane.setText("");
            }else{
                log("这是测试消息，source="+e.getSource() + ", ======  id=" + e.getID());
            }
        }
    }

    public void log(String msg) {
        //设置字体大小
        SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,12);
        //插入内容
        Document docs = logPane.getDocument();//获得文本对象
        String insert = "[ "+ DateUtil.currentDate()+" ]\t"+msg+"\n";
        try {
            docs.insertString(docs.getLength(), insert,attrset);//对文本进行追加
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

}
