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
        List<String> balance;
        String chatid = String.valueOf(chat.getId() > 0 ? chat.getId() : "m" + chat.getId() * (-1));
        try {
            balance = Calculus.Balance(username, chatid);
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        for (String balances : balance) {
            SendMessage message = new SendMessage();
            message.setChatId(chat.getId());
            message.setText(balances);
            try {
                absSender.execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
