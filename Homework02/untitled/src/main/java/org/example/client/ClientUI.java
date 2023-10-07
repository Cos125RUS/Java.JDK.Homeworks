package org.example.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

public class ClientUI extends JFrame implements ClientView {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final int POS_X = 200;
    private static final int POS_Y = 300;
    private static final String WINDOW_NAME = "Chat client";

    private final Client engin;
    private JButton btnLogin, btnSend;
    private JTextField fieldIP, fieldPort, fieldLogin, fieldEntry;
    private JPasswordField fieldPass;
    private Label stub;
    private TextArea textArea;

    public ClientUI(Client engin) {
        this.engin = engin;
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(POS_X, POS_Y);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(WINDOW_NAME);

        initComponents();
        JPanel panAuthorization = createAuthorizationPanel();
        JPanel panEntry = entryPanel();

        add(panAuthorization, BorderLayout.NORTH);
        add(textArea, BorderLayout.CENTER);
        add(panEntry, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initComponents() {
        this.textArea = new TextArea();
        textArea.setEditable(false);
        this.btnLogin = new JButton("Login");
        this.btnSend = new JButton("Send");
        this.fieldIP = new JTextField();
        this.fieldPort = new JTextField();
        this.fieldLogin = new JTextField();
        this.fieldPass = new JPasswordField();
        this.fieldEntry = new JTextField();
        this.stub = new Label();
        addAuthorizationListener();
        allLoginListener();
    }

    private void addAuthorizationListener() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionLogin();
            }
        });
    }

    private void allLoginListener() {
        pressEnterLoginListener(fieldIP);
        pressEnterLoginListener(fieldPort);
        pressEnterLoginListener(fieldLogin);
        pressEnterLoginListener(fieldPass);
    }

    private void pressEnterLoginListener(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    actionLogin();
            }
        });
    }

    private void actionLogin() {
        String ipValue = fieldIP.getText();
        String portValue = fieldPort.getText();
        String loginValue = fieldLogin.getText();
        String passValue = String.valueOf(fieldPass.getPassword());
        engin.authorization(ipValue, portValue, loginValue, passValue);
    }

    @Override
    public void setUserData(String[] userData) {
        fieldIP.setText(userData[0]);
        fieldPort.setText(String.valueOf(userData[1]));
        fieldLogin.setText(userData[2]);
        fieldPass.setText(userData[3]);
    }

    public void addMessageListener() {
        buttonMessageListener();
        pressEnterMessageListener();
    }

    private void buttonMessageListener() {
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionMessage();
            }
        });
    }

    private void pressEnterMessageListener() {
        fieldEntry.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    actionMessage();
            }
        });
    }

    private void actionMessage() {
        String[] date = Calendar.getInstance().getTime().toString().split(" ");
        String timeStamp = String.format(date[2] + " " + date[1] + " " + date[3]);
        String entryLine = String.format("[" + timeStamp + "] (" + fieldLogin.getText() + ") >> "
                + fieldEntry.getText());
        engin.sendMessage(entryLine);
        fieldEntry.setText("");
    }

    private JPanel createAuthorizationPanel() {
        JPanel panAuthorization = new JPanel(new GridLayout(2, 3));
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


    @Override
    public void printMessage(String message) {
        textArea.append(message);
    }
}
