package org.example;

import javax.swing.*;
import java.awt.*;

public class UI extends JFrame implements ImplUI{
    protected TextArea textArea;

    protected MessageHandler mh;

    @Override
    public void newMessage(String message) {
        textArea.append(message + '\n');
    }
}
