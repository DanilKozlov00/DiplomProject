package com.bot.controller;

import com.bot.model.Student;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.bot.utils.RegistrationValidator;
import com.bot.utils.ValidatorException;

import java.util.List;

public class UserSession {

    private static final String REGISTRATION = "registration";
    private static final String LOGIN = "login";
    private static final String SLASH = "/";
    private static final String JOURNAL = "journal";
    private static final String EXIT = "exit";
    private static final String CHECK = "check";
    private static final String NOT_AUTH = "Вы не авторизованы";
    private static final String EXIT_IN_SYSTEM = "Вы вышли из системы";
    private static final String SPACE = " ";
    private static final String START = "start";
    private static final int LOGIN_MESSAGE_LENGTH = 3;
    private static final int LOGIN_NICKNAME_POSITION = 1;
    private static final int LOGIN_PASSWORD_POSITION = 2;
    private static final int REGISTRATION_NICKNAME_POSITION = 1;
    private static final int REGISTRATION_PASSWORD_POSITION = 2;
    private static final int REGISTRATION_MESSAGE_LENGTH = 3;
    private static final int CHECK_MESSAGE_LENGTH = 4;
    private static final int CHECK_DISCIPLINE_POSITION = 2;
    private static final int CHECK_TASK_NUMBER_POSITION = 3;
    private static final int CHECK_USER_IP_POSITION = 1;

    private static final Bot bot = Bot.getBot();
    private Student currentStudent;
    MessageWorker messageWorker;

    public UserSession(long chatId) {
        messageWorker = new MessageWorker(chatId);
    }


    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public void choiceAction(String message) {

        try {
            if ((SLASH + START).equals(message)) {
                bot.execute(messageWorker.start());
            }

            if (message.startsWith(SLASH + LOGIN)) {
                String[] messageWords = message.split(SPACE);
                if (messageWords.length == LOGIN_MESSAGE_LENGTH) {
                    List<SendMessage> messages = messageWorker.login(messageWords[LOGIN_NICKNAME_POSITION], messageWords[LOGIN_PASSWORD_POSITION], this);
                    for (SendMessage sendMessage : messages) {
                        bot.execute(sendMessage);
                    }
                }
            }

            if ((SLASH + JOURNAL).equals(message)) {
                if (currentStudent == null) {
                    bot.execute(messageWorker.sendMessage(NOT_AUTH));
                    return;
                }
                List<SendMessage> messages = messageWorker.getStudentTasks(currentStudent);
                for (SendMessage sendMessage : messages) {
                    bot.execute(sendMessage);
                }
            }

            if (message.startsWith(SLASH + CHECK)) {
                if (currentStudent == null) {
                    bot.execute(messageWorker.sendMessage(NOT_AUTH));
                    return;
                }
                String[] messageWords = message.split(SPACE);

                if (messageWords.length == CHECK_MESSAGE_LENGTH) {
                    try {
                        RegistrationValidator.checkLoginValues(messageWords[CHECK_DISCIPLINE_POSITION], messageWords[CHECK_TASK_NUMBER_POSITION], messageWords[CHECK_USER_IP_POSITION]);
                    } catch (ValidatorException validatorException) {
                        bot.execute(messageWorker.sendMessage(validatorException.getMessage()));
                        return;
                    }
                    bot.execute(messageWorker.check(currentStudent.getNickname(), messageWords[2], Integer.parseInt(messageWords[3]), messageWords[1]));
                }

            }

            if (message.startsWith(SLASH + REGISTRATION)) {
                String[] messageWords = message.split(SPACE);
                if (messageWords.length == REGISTRATION_MESSAGE_LENGTH) {
                    try {
                        RegistrationValidator.checkRegistrationValues(messageWords[REGISTRATION_NICKNAME_POSITION]);
                    } catch (ValidatorException validatorException) {
                        bot.execute(messageWorker.sendMessage(validatorException.getMessage()));
                        return;
                    }
                    bot.execute(messageWorker.registerStudent(messageWords[REGISTRATION_NICKNAME_POSITION], messageWords[REGISTRATION_PASSWORD_POSITION]));
                    bot.execute(messageWorker.sendMessage("Если все успешно вы можете авторизоваться:\n" +
                            "/login <Никнейм> <Пароль>"));
                }
            }

            if (message.startsWith(SLASH + EXIT)) {
                currentStudent = null;
                bot.execute(messageWorker.sendMessage(EXIT_IN_SYSTEM));
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
