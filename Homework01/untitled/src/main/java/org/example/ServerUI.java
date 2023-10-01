package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerUI extends UI {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final int POS_X = 800;
    private static final int POS_Y = 300;

    private JButton btnStart, btnStop;
    private ServerEngin engin;

    public ServerUI(ServerEngin engin, MessageHandler mh) {
        setWindowOptions();
        initComponents(engin, mh);

        JPanel panButtons = new JPanel(new GridLayout(1, 2));
        panButtons.add(btnStart);
        panButtons.add(btnStop);

        add(textArea);
        add(panButtons, BorderLayout.SOUTH);

        setVisible(true);

    }

    private void setWindowOptions(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(POS_X, POS_Y);
        setResizable(false);
        setTitle("Chat Server");
    }

    private void initComponents(ServerEngin engin, MessageHandler mh) {
        super.mh = mh;
        super.textArea = new TextArea();
        textArea.setEditable(false);
        this.engin = engin;
        this.btnStart = new JButton("Start");
        this.btnStop = new JButton("Stop");
        addBtnListeners();
    }

    private void addBtnListeners(){
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });
    }

    private void startServer(){
        switch (engin.run()) {
            case 0 -> textArea.append("Server Started\n");
            case 1 -> textArea.append("Server Already Started\n");
            case 2 -> textArea.append("Fail\n");
        }
//        TODO: Добавить блокировку MessageHandler-а и авторизации до старта сервера
    }

    private void stopServer() {
//        TODO: Добавить блокировку MessageHandler-а и авторизации после старта сервера
        textArea.append("Stop Server\n");
    }
}
