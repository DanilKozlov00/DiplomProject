package com.bot.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationValidator {

    private final static Pattern NICKNAME_PATTERN = Pattern.compile("^[A-Za-z0-9А-яа-я]+([A-Za-z0-9А-яа-я]*|[._-]?[A-Za-z0-9А-яа-я]+)*$");
    private final static Pattern DISCIPLINE_PATTERN = Pattern.compile("^[А-яа-яEё ]+");
    private final static Pattern IP_PATTERN = Pattern.compile("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$");

    public static void checkRegistrationValues(String nickName) throws ValidatorException {

        Matcher matcher = NICKNAME_PATTERN.matcher(nickName);
        if (!matcher.find()) {
            throw new ValidatorException("Error create User with nickname '" + nickName + "' is incorrect nickname try another");
        }

    }

    public static void checkLoginValues(String discipline, String taskNumber, String ip) throws ValidatorException {

        Matcher matcher = DISCIPLINE_PATTERN.matcher(discipline);
        if (!matcher.find()) {
            throw new ValidatorException(discipline + "is incorrect discipline name. Use Russian language");
        }
        try {
            Integer.parseInt(taskNumber);
        } catch (NumberFormatException numberFormatException) {
            throw new ValidatorException("Task number is not decimal digit");
        }

        matcher = IP_PATTERN.matcher(ip);
        if (!matcher.find()) {
            throw new ValidatorException("Ip address is incorrect");
        }
    }


}
