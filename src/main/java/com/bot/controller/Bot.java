package com.bot.controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {

    private static final Bot bot = new Bot();
    private final Map<Long, UserSession> userSessions = new HashMap<>();

    public static Bot getBot() {
        return bot;
    }

    @Override
    public void onUpdateReceived(Update update) {
        UserSession currentSession = null;
        String message = "";
        if (update.hasMessage()) {
            currentSession = getUserSession(update.getMessage().getChatId());
            message = update.getMessage().getText();
        }
        if (update.hasCallbackQuery()) {
            currentSession = getUserSession(update.getCallbackQuery().getMessage().getChatId());
            message = update.getCallbackQuery().getData();
        }
        if (currentSession != null) {
            currentSession.choiceAction(message);
        }
    }

    private UserSession getUserSession(Long chatId) {
        UserSession currentSession = userSessions.get(chatId);
        if (currentSession == null) {
            currentSession = new UserSession(chatId);
            userSessions.put(chatId, currentSession);
        }
        return currentSession;
    }

    @Override
    public String getBotUsername() {
        return "KSU";
    }

    @Override
    public String getBotToken() {
        return "***";
    }

}

