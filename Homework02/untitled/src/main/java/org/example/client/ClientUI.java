package org.example.client;

import javax.swing.*;
import java.awt.*;

public class ClientUI extends JFrame {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final int POS_X = 200;
    private static final int POS_Y = 300;
    private static final String WINDOW_NAME = "Chat client";
    private static final String USER_DATA_FILE = "src/files/client/user.txt";

    private JButton btnLogin, btnSend;
    private JTextField fieldIP, fieldPort, fieldLogin, fieldEntry;
    private JPasswordField fieldPass;
    private Label stub;
    private TextArea textArea;

}
