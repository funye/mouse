package com.fun.ui;

import com.fun.http.HttpServer;
import com.fun.utils.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by yehuan on 2016/11/7.
 *
 */
public class MouseFrame extends JFrame{

    private static final int WIDTH=400;
    private static final int HEIGHT=200;

    private JButton btn;
    private JTextArea logArea;
    private JTextField hostInput;
    private JTextField portInput;

    private HttpServer server;
    private Thread serverThread;


    public MouseFrame(String title) {

        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize(WIDTH,HEIGHT);
        setLocation(750,300);

        JLabel host = new JLabel(Language.get("ui.host"));
        host.setHorizontalAlignment(SwingConstants.RIGHT);
        hostInput = new JTextField(15);

        JLabel port = new JLabel(Language.get("ui.port"));
        port.setHorizontalAlignment(SwingConstants.RIGHT);
        portInput = new JTextField(15);

        btn = new JButton(Language.get("ui.start"));
        btn.addActionListener(new MyClickListener());

        logArea = new JTextArea("",15,35);
        logArea.setMargin(new Insets(0,0,15,0));

        JPanel p0 = new JPanel();
        p0.add(host);
        p0.add(hostInput);

        JPanel p1 = new JPanel();
        p1.add(port);
        p1.add(portInput);

        JPanel p2 = new JPanel();
        p2.add(btn);

        JPanel p3 = new JPanel();
        p3.add(logArea);

        add(p0);
        add(p1);
        add(p2);
        add(p3);

    }


    public void showUI(){
        setVisible(true);
    }

    public static void main(String[] args) {
        new MouseFrame(Language.get("ui.title")).showUI();
    }

    class MyClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("click...");
        }
    }
}
