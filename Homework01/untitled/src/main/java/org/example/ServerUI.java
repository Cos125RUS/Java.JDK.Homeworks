package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerUI extends JFrame {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final int POS_X = 800;
    private static final int POS_Y = 300;

    private TextArea textArea;
    private JButton btnStart, btnStop;

    private MessageHandler mh;

    public ServerUI(MessageHandler mh) {
        setWindowOptions();
        initComponents(mh);

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

    private void initComponents(MessageHandler mh) {
        this.mh = mh;
        this.textArea = new TextArea();
        textArea.setEditable(false);
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
        textArea.append("Server Started\n");
//        TODO: Добавить блокировку MessageHandler-а и авторизации до старта сервера
    }

    private void stopServer() {
//        TODO: Добавить блокировку MessageHandler-а и авторизации после старта сервера
        textArea.append("Stop Server\n");
    }

    public void newMessage(String message) {
//        TODO: убрать в супер-класс
//        TODO: добавить логирование в файл
        textArea.append(message + '\n');
    }
}
