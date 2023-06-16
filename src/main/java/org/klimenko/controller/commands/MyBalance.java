package org.klimenko.controller.commands;

import org.klimenko.service.Calculus;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class MyBalance extends BotCommand {

    public MyBalance() {
        super("balance", "How much money I owe");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String username = user.getUserName();
        List<String> balances;
        String chatid = String.valueOf(chat.getId() > 0 ? chat.getId() : "m" + chat.getId() * (-1));
        try {
            balances = Calculus.Balance(username, chatid, chat);
            for (String balance : balances) {
                if (balances.isEmpty()) {
                    balance = "YAY! You got no any debts!";
                }
                SendMessage message = new SendMessage();
                message.setChatId(chat.getId());
                message.setText(balance);
                try {
                    absSender.execute(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            sendExceptionMessage(e, absSender, chat);
        }

    }

    private void sendExceptionMessage(Exception e, AbsSender absSender, Chat chat) {
        String whatHappened = e.getMessage();
        SendMessage someException = new SendMessage();
        someException.setChatId(chat.getId().toString());
        someException.setText(whatHappened);
        try {
            absSender.execute(someException);
        } catch (TelegramApiException f) {
            System.out.println(f);
        }
    }
}
