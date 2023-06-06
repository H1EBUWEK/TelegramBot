package org.klimenko.controller;

import java.util.Map;

public class BotConfig {
    public static String BOT_NAME;
    public static String BOT_TOKEN;

    public static void GetBotConfig(){
        BOT_NAME = System.getenv("BOT_NAME");
        BOT_TOKEN = System.getenv("BOT_TOKEN");
    }
}
