package org.klimenko.controller.commands;


import org.klimenko.DAO;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;


public class NewUser extends BotCommand{

    public NewUser() {
        super("registration", "Registration command");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        long id = user.getId();
        String username = user.getUserName();
        try {
            if (!DAO.CheckTinId(id)) {
                DAO.AddToTin(id,username);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
