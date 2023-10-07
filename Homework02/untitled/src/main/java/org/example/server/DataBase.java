package org.example.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface DataBase {
//    HashMap<String, Connection> getUserList();
    String getHistory() throws IOException;
//    void addLog();
    void addMessage(String message);
}
