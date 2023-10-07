package org.example.client;

public class Validator implements Validation{
    @Override
    public int checkValue(String ipValue, String portValue, String loginValue, String passValue) {
        try {
            if (!checkIP(ipValue)) return 1;
            if (!checkPort(portValue)) return 2;
            if (!checkLength(loginValue)) return 3;
            if (!checkLength(passValue)) return 4;
        } catch (RuntimeException e) {
//            textArea.append("Unknown error");
        }
        return 0;
    }


    private boolean checkIP(String value) {
        String[] bits = value.replace(".", ":").split(":");
        if (bits.length != 4)
            return false;
        else {
            for (String bit : bits) {
                int b = -1;
                try {
                    b = Integer.parseInt(String.valueOf(bit));
                } catch (NumberFormatException e) {
                    return false;
                }
                if (b < 0 || b > 255)
                    return false;
                if (Integer.parseInt(String.valueOf(bits[0])) == 0 ||
                        Integer.parseInt(String.valueOf(bits[3])) == 0)
                    return false;
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
}
