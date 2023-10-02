package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ServerEngin {
    private static final String CHAT_HISTORY_PATH = "server/ChatHistory.txt";
    private static final String CLIENT_LIST_PATH = "server/ClientList.txt";
    private static final String SOCKET = "127.0.0.1:8988";
    private final MessageHandler mh;
    private boolean isRun;
    private Map<String, String> members;

    public ServerEngin() {
        this.isRun = false;
        this.members = new HashMap<>();
        this.mh = new MessageHandler();
        ServerUI serverUI = new ServerUI(this, mh);
        mh.addMember(serverUI);
        loadMembersList();
    }


    public int run() {
//            Имитация запуска сервера
        try {
            if (!isRun) {
                isRun = true;
                mh.run();
                return 0;
            } else if (isRun) {
                return 1;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
        return 2;
    }

    public int stop() {
//            Имитация остановки сервера
        try {
            if (isRun) {
                isRun = false;
                mh.newMessage("Connection Lost");
                mh.stop();
                return 0;
            } else if (!isRun) {
                return 1;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
        return 2;
    }

    public boolean authorize(String loginValue, String passValue, ClientUI client) {
        if (isRun) {
            if (checkUser(loginValue, passValue)) {
                newClient(client);
                return true;
            } else
                return false;
        } else
            return false;
    }

    private boolean checkUser(String loginValue, String passValue) {
        if (searchInMembers(loginValue)) {
            return (members.get(loginValue).equals(passValue));
        }
        else
            newMember(loginValue, passValue);
        return true;
    }

    private boolean searchInMembers(String loginValue){
        try {
            members.get(loginValue);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private void newMember(String loginValue, String passValue) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CLIENT_LIST_PATH, true))) {
            bw.append(String.format(loginValue + "/" + passValue));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void newClient(ClientUI client) {
        mh.addMember(client);
    }

    public void newMessage(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CHAT_HISTORY_PATH, true))) {
            bw.append(String.format(message + '\n'));
            bw.flush();
            mh.newMessage(message);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getHistory() {
        String history = loadHistory();
        mh.newMessage(history);
    }

    private String loadHistory() {
        String history = "";
        try (BufferedReader br = new BufferedReader(new FileReader(CHAT_HISTORY_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                history += line + '\n';
            }
            return history;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            return history;
        }
    }

    private void loadMembersList() {
        try (BufferedReader br = new BufferedReader(new FileReader(CLIENT_LIST_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] user = line.split("/");
                members.put(user[0], user[1]);
            }
        } catch (FileNotFoundException e) {
            try {
                Files.createFile(Paths.get(CLIENT_LIST_PATH));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
