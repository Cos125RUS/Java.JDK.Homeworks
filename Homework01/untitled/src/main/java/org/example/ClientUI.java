package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ClientUI extends UI {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final int POS_X = 200;
    private static final int POS_Y = 300;
    private static final String WINDOW_NAME = "Chat client";
    private static final String USER_DATA_FILE = "client/user.txt";


    private JButton btnLogin, btnSend;
    private JTextField fieldIP, fieldPort, fieldLogin, fieldEntry;
    private JPasswordField fieldPass;
    private Label stub;

    private final ServerEngin server;

    public ClientUI(ServerEngin server) {
        this.server = server;
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(POS_X, POS_Y);
        setResizable(false);
        setTitle(WINDOW_NAME);

        initComponents();
        JPanel panAuthorization = createAuthorizationPanel();
        JPanel panEntry = entryPanel();

        add(panAuthorization, BorderLayout.NORTH);
        add(textArea, BorderLayout.CENTER);
        add(panEntry, BorderLayout.SOUTH);

        loadUserData();
        setVisible(true);
    }

    private void initComponents() {
        super.textArea = new TextArea();
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
    }

    private void addAuthorizationListener() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipValue = fieldIP.getText();
                String portValue = fieldPort.getText();
                String loginValue = fieldLogin.getText();
                String passValue = String.valueOf(fieldPass.getPassword());
                authorization(ipValue, portValue, loginValue, passValue);
            }
        });
    }

    private void loadUserData() {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            fieldIP.setText(br.readLine());
            fieldPort.setText(br.readLine());
            fieldLogin.setText(br.readLine());
            fieldPass.setText(br.readLine());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void authorization(String ipValue, String portValue, String loginValue, String passValue) {
        int check = 5;
        if ((check = checkValue(ipValue, portValue, loginValue, passValue)) == 0) {
            save(ipValue, portValue, loginValue, passValue);
            if (server.authorize(ipValue, portValue, loginValue, passValue, this)) {
                addMessageListener();
                server.getHistory();
            } else {
                textArea.append("There is no connection with the server\n");
            }
        } else {
            switch (check) {
                case 1 -> textArea.append("Bad value of IP");
                case 2 -> textArea.append("Bad value of port number");
                case 3 -> textArea.append("Bad value of login");
                case 4 -> textArea.append("Bad value of password");
                case 5 -> textArea.append("Entry error");
            }
        }
    }

    private int checkValue(String ipValue, String portValue, String loginValue, String passValue) {
        try {
            if (!checkIP(ipValue)) return 1;
            if (!checkPort(portValue)) return 2;
            if (!checkLength(loginValue)) return 3;
            if (!checkLength(passValue)) return 4;
        } catch (RuntimeException e) {
            throw new RuntimeException("Unknown error");
        }
        return 0;
    }

    private boolean checkIP(String value) {
        String[] bits = value.replace(".", ":").split(":");
        if (bits.length != 4)
            return false;
        else {
            for (String bit : bits) {
                try {
                    Integer.parseInt(String.valueOf(bit));
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkPort(String value) {
        try {
            int port = Integer.parseInt(String.valueOf(value));
            if (port < 1 || port > 65535 || port == 80) // и т.д.
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean checkLength(String value) {
        return value.length() >= 3 && value.length() <= 15;
    }

    private void save(String IPValue, String PortValue, String LoginValue, String PassValue) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            bw.write(String.format(IPValue + '\n' + PortValue + '\n' + LoginValue + '\n' +
                    PassValue));
        } catch (IOException e) {
            throw new RuntimeException("Write user data is fail");
        }
    }

    private void addMessageListener() {
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.newMessage(fieldEntry.getText());
                fieldEntry.setText("");
            }
        });
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
}
