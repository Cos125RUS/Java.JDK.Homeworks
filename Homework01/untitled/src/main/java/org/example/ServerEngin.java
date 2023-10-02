package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ServerEngin {
    private static final String CHAT_HISTORY_PATH = "server/ChatHistory.txt";
    private static final String CLIENT_LIST_PATH = "server/ClientList.txt";
    //    private static final String SOCKET = "127.0.0.1:8988";
    private final MessageHandler mh;
    private boolean isRun;
    private Map<String, String> members;

    public ServerEngin() {
        this.isRun = false;
        this.members = new HashMap<>();
        this.mh = new MessageHandler();
        ServerUI serverUI = new ServerUI(this, mh);
        loadMembersList();
        mh.addMember(serverUI);
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
        } catch (StartServerException e) {
            mh.newMessage(e.getMessage());
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
        } catch (StopServerException e) {
            e.printStackTrace();
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
        } else
            newMember(loginValue, passValue);
        return true;
    }

    private boolean searchInMembers(String loginValue) {
        return (members.get(loginValue) != null);
    }

    private void newMember(String loginValue, String passValue) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CLIENT_LIST_PATH, true))) {
            bw.append(String.format(loginValue + "/" + passValue));
        } catch (FileNotFoundException e) {
            try {
                Files.createFile(Paths.get(CLIENT_LIST_PATH));
            } catch (IOException ex) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        } catch (ConnectionException e) {
            mh.newMessage(e.getMessage());
        } catch (IOException e) {
            mh.newMessage("Error");
        }
    }

    public void getHistory() {
        String history = loadHistory();
        if (history.length() == 0)
            history = "Welcome to chat\n";
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return history;
        }
    }

    private void loadMembersList() {
        try {
            Files.createDirectories(Paths.get("server"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
