package org.example.client;

public interface Validation {
    /**
     * Проверка введённых данных на валидность
     * @param ipValue
     * @param portValue
     * @param loginValue
     * @param passValue
     * @return код ошибки
     */
    int checkValue(String ipValue, String portValue, String loginValue, String passValue);
}
