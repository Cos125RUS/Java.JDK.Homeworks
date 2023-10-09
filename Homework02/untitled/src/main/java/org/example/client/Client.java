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
    private final ClientView ui;
    private final Validation validator;

    private Connect connect;
    private String host;
    private int port;
    private String login;
    private String password;
    private String buffer;

    public Client() {
        this.host = "";
        this.port = 0;
        this.login = "";
        this.password = "";
        this.buffer = "";
        this.validator = new Validator();
        this.ui = new ClientUI(this);
        ui.setUserData(getUserData());
    }

    public static void main(String[] args) {
        new Client();
    }

    /**
     * Отправка пользовательских данных
     *
     * @return [host, port, login, pass]
     */
    public String[] getUserData() {
        loadUserData();
        return new String[]{host, String.valueOf(port), login, password};
    }

    /**
     * Загрузка данных пользователя из файла
     */
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

    /**
     * Авторизация на сервере
     *
     * @param ipValue
     * @param portValue
     * @param loginValue
     * @param passValue
     */
    public void authorization(String ipValue, String portValue, String loginValue, String passValue) {
        int check = 5;
        if ((check = validator.checkValue(ipValue, portValue, loginValue, passValue)) == 0) {
            save(ipValue, portValue, loginValue, passValue);
            ui.addMessageListener();
            try {
                this.connect = new Connect(this, ipValue, Integer.parseInt(portValue), loginValue,
                        passValue);
                connect.start();
            } catch (IOException e) {
                ui.printMessage(e.getMessage());
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

    /**
     * Сохранение введённых пользователем данных
     *
     * @param IPValue
     * @param PortValue
     * @param LoginValue
     * @param PassValue
     */
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

    /**
     * Печать нового сообщения
     *
     * @param message
     */
    public void printMessage(String message) {
        ui.printMessage(message);
    }

    /**
     * Запись введённого пользователем сообщения в буфер
     *
     * @param message
     */
    public void newMessage(String message) {
        buffer = message;
    }

    /**
     * Отправка сообщения, хранящегося в буфере
     *
     * @return
     */
    public String sendMessage() {
        String message = buffer;
        buffer = "";
        return message;
    }

    /**
     * Проверка состояния подключения
     *
     * @param answer
     */
    public void check(boolean answer) {
        if (answer) {
            ui.authorization();
        } else {
            ui.printMessage("Ошибка авторизации! В доступе отказано\n");
        }
    }
}
