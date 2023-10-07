package org.example.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Графический интерфейс на стороне сервера
 */
public class ServerUI extends JFrame implements ServerView{
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final int POS_X = 800;
    private static final int POS_Y = 300;
    private static final String WINDOW_NAME = "Chat Server";

    private JButton btnStart, btnStop;
    private TextArea textArea;

    private Server server;


//    protected MessageHandler mh;

    public ServerUI(Server server) {
        setWindowOptions();
        initComponents(server);

        JPanel panButtons = new JPanel(new GridLayout(1, 2));
        panButtons.add(btnStart);
        panButtons.add(btnStop);

        add(textArea);
        add(panButtons, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Задать значения отрисовки окна
     */
    private void setWindowOptions(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(POS_X, POS_Y);
        setResizable(false);
        setTitle(WINDOW_NAME);
    }

    /**
     * Инициировать активные компоненты
     * @param server движок
     */
    private void initComponents(Server server) {
//        this.mh = mh;
        this.server = server;
        this.textArea = new TextArea();
        textArea.setEditable(false);
        this.btnStart = new JButton("Start");
        this.btnStop = new JButton("Stop");
        addBtnListeners();
    }

    /**
     * Добавоение слушателей кнопок
     */
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

    @Override
    public void printLog(String log) {
        printMessage(log);
    }

    @Override
    public void printMessage(String message) {
        textArea.append(message + '\n');
    }

    @Override
    public void startServer() {
        switch (server.startServer()) {
            case 0 -> textArea.append("Сервер запущен\n");
            case 1 -> textArea.append("Сервер уже запущен\n");
            case 2 -> textArea.append("Ошибка создания serverSocket-а\n");
            case 666 -> textArea.append("Неизвестная ошибка\n");
        }
    }

    @Override
    public void stopServer() {
        switch (server.stopServer()) {
            case 0 -> textArea.append("Сервер остановлен\n");
            case 1 -> textArea.append("Сервер уже остановлен\n");
            case 666 -> textArea.append("Неизвестная ошибка\n");
        }
    }
}
