package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientUI extends JFrame {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final int POS_X = 200;
    private static final int POS_Y = 300;


    private JButton btnLogin, btnSend;
    private JTextField fieldIP, fieldPort, fieldLogin, fieldPass, fieldEntry;
    private Label stub;
    private TextArea textArea;

    private MessageHandler mh;

    public ClientUI(MessageHandler mh) {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(POS_X, POS_Y);
        setResizable(false);
        setTitle("Chat client");

        initComponents(mh);
        JPanel panAuthorization = createAuthorizationPanel();
        JPanel panEntry = entryPanel();

        add(panAuthorization, BorderLayout.NORTH);
        add(textArea, BorderLayout.CENTER);
        add(panEntry, BorderLayout.SOUTH);

        setVisible(true);

    }

    private void initComponents(MessageHandler mh) {
        this.mh = mh;
        this.textArea = new TextArea();
        textArea.setEditable(false);
        this.btnLogin = new JButton("Login");
        this.btnSend = new JButton("Send");
        addBtnListeners();
        this.fieldIP = new JTextField();
        this.fieldPort = new JTextField();
        this.fieldLogin = new JTextField();
        this.fieldPass = new JTextField();
//        TODO: Добавить скрытие
        this.fieldEntry = new JTextField();
        this.stub = new Label();
    }

    private void addBtnListeners(){
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String IPValue = fieldIP.getText();
                String PortValue = fieldPort.getText();
                String LoginValue = fieldLogin.getText();
                String PassValue = fieldPass.getText();
                authorization(IPValue, PortValue, LoginValue, PassValue);
//                TODO: Скрыть блок авторизации
            }
        });
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mh.newMessage(fieldEntry.getText());
                fieldEntry.setText("");
            }
        });
    }

    private void authorization(String IPValue, String PortValue, String LoginValue, String PassValue){
        textArea.append("Authorization\n");
//        TODO: Добавить чекеры
//        TODO: Добавить авторизацию
    }

    private void sendMessage(String message){
        textArea.append(message + '\n');
//        TODO: добавить отправку сообщения на сервер
    }

    private JPanel createAuthorizationPanel() {
        JPanel panAuthorization = new JPanel(new GridLayout(2,3));
        panAuthorization.add(fieldIP);
        panAuthorization.add(fieldPort);
        panAuthorization.add(stub);
        panAuthorization.add(fieldLogin);
        panAuthorization.add(fieldPass);
        panAuthorization.add(btnLogin);
        return panAuthorization;
    }

    private JPanel entryPanel() {
        JPanel panEntry = new JPanel(new GridBagLayout());
        GridBagConstraints bag = new GridBagConstraints();
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.weightx = 1;
        bag.weighty = 0;
        panEntry.add(fieldEntry, bag);
        panEntry.add(btnSend);
        return panEntry;
    }

    public void newMessage(String message) {
//        TODO: убрать в супер-класс
        textArea.append(message + '\n');
    }
}
