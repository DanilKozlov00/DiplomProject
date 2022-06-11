package com.bot.controller;

import com.bot.model.Student;
import com.bot.model.Task;
import com.bot.api.RightechController;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageWorker {

    private final String chatId;

    RightechController rightechController = new RightechController();

    public MessageWorker(long chatId) {
        this.chatId = String.valueOf(chatId);
    }

    public SendMessage start() {
        return sendMessage("Для начала работы с ботом необходимо зарегистрироваться в системе\n" +
                "Для этого воспользуйтесь следующей командой\n" +
                "/registration <Никнейм> <Пароль>");
    }

    public List<SendMessage> login(String nickname, String password, UserSession userSession) {
        List<SendMessage> messages = new ArrayList<>();
        Student currentStudent = rightechController.getStudentByNickname(nickname);
        if (currentStudent == null) {
            messages.add(new SendMessage(chatId, "Данный пользователь отсутсвует в системе"));
            return messages;
        }
        if (new Argon2PasswordEncoder().matches(password, currentStudent.getPassword())) {
            userSession.setCurrentStudent(currentStudent);
            messages.add(sendMessage("Успешный вход."));
            messages.add(getInfoMessageAfterLogin());
        } else {
            messages.add(new SendMessage(chatId, "Пароль неверный"));
        }
        return messages;
    }

    public List<SendMessage> getStudentTasks(Student student) {
        List<Task> studentTasks = rightechController.getStudentTasksByStudentId(student.getId());
        List<SendMessage> messages = new ArrayList<>();
        if (studentTasks.isEmpty()) {
            messages.add(new SendMessage(chatId, "Работы отсутствуют"));
            return messages;
        }

        for (Task task : studentTasks) {
            messages.add(new SendMessage(chatId, "Дисциплина: " +
                    task.getDiscipline() +
                    "\nНомер работы: " + task.getNumber() +
                    "\nОценка: " + task.isGrade()));
        }
        return messages;
    }

    public SendMessage sendMessage(String messageText) {
        return new SendMessage(chatId, messageText);
    }

    public SendMessage registerStudent(String nickname, String password) {
        return sendMessage(rightechController.registerStudent(nickname, password));
    }

    public SendMessage check(String nickname, String discipline, int taskNumber, String ip) {
        return sendMessage(rightechController.check(nickname, discipline, taskNumber, ip));
    }

    private SendMessage getInfoMessageAfterLogin() {
        return sendMessage("На данный момент вам доступны следующие команды:\n" +
                "1. Для получения журнала введите /journal\n" +
                "2. Для отправки задания на проверку введите\n" +
                "/check <Ваш ip в сети ZeroTier> <Название дисциплины> <Номер проверяемой работы>\n" +
                "3. Для деавторизации введите /exit");
    }
}
