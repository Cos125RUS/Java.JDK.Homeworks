package org.example;

import javax.swing.*;
import java.awt.*;

public class ClientUI extends JFrame {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final int POS_X = 200;
    private static final int POS_Y = 300;


    private JButton btnLogin, btnSend;
    private JTextField fieldIP, fieldPort, fieldLogin, fieldPass, fieldEntry;
    private Label stub;
    private TextArea textArea;

    public ClientUI() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(POS_X, POS_Y);
        setResizable(false);
        setTitle("Chat client");

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
        this.btnLogin = new JButton("Login");
        this.btnSend = new JButton("Send");
        this.fieldIP = new JTextField();
        this.fieldPort = new JTextField();
        this.fieldLogin = new JTextField();
        this.fieldPass = new JTextField();
        this.fieldEntry = new JTextField();
        this.stub = new Label();
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
}
