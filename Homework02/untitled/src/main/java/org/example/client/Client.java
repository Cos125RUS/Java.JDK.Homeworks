package org.example.client;

import org.example.client.exceptions.AccessErrorException;
import org.example.client.exceptions.LoadUserDataException;
import org.example.client.exceptions.WriteUserDataException;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Client {
    private static final String USER_DATA_FILE = "src/files/client/user.txt";
    private final ClientUI ui;
    private final Validation validator;

    private Socket socket;
    private Connect connect;
    private String host;
    private int port;
    private String login;
    private String password;
    private boolean isConnection;

    public Client() {
        this.host = "";
        this.port = 0;
        this.login = "";
        this.password = "";
        this.validator = new Validator();
        this.ui = new ClientUI(this);
        ui.setUserData(getUserData());
    }

    public static void main(String[] args) {
        new Client();
    }

    public String[] getUserData() {
        loadUserData();
        return new String[] {host, String.valueOf(port), login,password};
    }

    private void loadUserData() {
        try {
            Files.createDirectories(Paths.get("src/files/client"));
        } catch (AccessErrorException e) {
            ui.printMessage(e.getMessage());
        } catch (IOException e) {
            ui.printMessage(e.getMessage());
        }
        try (BufferedReader br = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            this.host = br.readLine();
            this.port = Integer.parseInt(br.readLine());
            this.login = br.readLine();
            this.password = br.readLine();
        } catch (LoadUserDataException e) {
            ui.printMessage(e.getMessage());
        } catch (FileNotFoundException e) {
            try {
                Files.createFile(Paths.get(USER_DATA_FILE));
            } catch (AccessErrorException ex) {
                ui.printMessage(e.getMessage());
            } catch (IOException ex) {
                ui.printMessage(e.getMessage());
            }
        } catch (NumberFormatException | NullPointerException | IOException e) {
            ui.printMessage(e.getMessage());
        }
    }

    public void authorization(String ipValue, String portValue, String loginValue, String passValue) {
        int check = 5;
        if ((check = validator.checkValue(ipValue, portValue, loginValue, passValue)) == 0) {
            save(ipValue, portValue, loginValue, passValue);
            try {
                this.connect = new Connect(this, ipValue, Integer.parseInt(portValue), loginValue,
                        passValue);
                connect.start();
            } catch (IOException e) {
                ui.printMessage(e.getMessage());
            }
            if (isConnection) {
                ui.addMessageListener();
//                TODO запрос истории
            } else {
                ui.printMessage("Не удалось установить соединение с сервером\n");
            }
        } else {
            switch (check) {
                case 1 -> ui.printMessage("Bad value of IP\n");
                case 2 -> ui.printMessage("Bad value of port number\n");
                case 3 -> ui.printMessage("Bad value of login\n");
                case 4 -> ui.printMessage("Bad value of password\n");
                case 5 -> ui.printMessage("Entry error\n");
            }
        }
    }

    private void save(String IPValue, String PortValue, String LoginValue, String PassValue) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            bw.write(String.format(IPValue + '\n' + PortValue + '\n' + LoginValue + '\n' +
                    PassValue));
        } catch (WriteUserDataException e) {
            ui.printMessage(e.getMessage());
        } catch (IOException e) {
            ui.printMessage(e.getMessage());
        }
    }

    public void printMessage(String message) {
        ui.printMessage(message);
    }

    public void sendMessage(String message) {
        connect.send(message);
    }

    public void check(boolean answer) {
        isConnection = answer;
    }
}
