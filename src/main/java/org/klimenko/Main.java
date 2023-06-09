package org.klimenko;


import org.klimenko.controller.MyBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Main {

    public static Properties properties = new Properties();

    public static void main(String[] args) {

        String filepath = properties.getProperty("BOT_CONF");
        try (FileInputStream fis = new FileInputStream(filepath)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public static List<Person> personList = new ArrayList<>();
}