package org.klimenko.controller.commands;


import org.klimenko.service.Calculus;
import org.klimenko.DAO;
import org.klimenko.Parser;
import org.klimenko.WrongFormatException;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;


public class IOweYou extends BotCommand {

    public IOweYou() {
        super("ioweyou", "I owe you money");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String creditor = user.getUserName();
        String debtor;
        BigDecimal money;
        try {
            debtor = (String) Parser.ParsingMoney(strings).get("name");
            money = (BigDecimal) Parser.ParsingMoney(strings).get("amount");
        } catch (WrongFormatException e) {
            throw new RuntimeException(e);
        }

        try {
            if(!creditor.equals(debtor)) {
                if (DAO.CheckTinId(user.getId())) {
                    if (DAO.CheckTinUsername(debtor)) {
                        try {
                            Calculus.AddDebt(debtor, creditor, money);
                        } catch (SQLException | ClassNotFoundException | InvocationTargetException |
                                 NoSuchMethodException |
                                 InstantiationException | IllegalAccessException e) {
                            System.out.println(e);
                        }
                    } else {
                        //вывести что пользователя нет в базе. попросите его зарегестрироваться
                    }
                } else {
                    DAO.AddToTin(Math.toIntExact(user.getId()), user.getUserName());
                    if (DAO.CheckTinUsername(debtor)) {
                        try {
                            Calculus.AddDebt(debtor, creditor, money);
                        } catch (SQLException | ClassNotFoundException | InvocationTargetException |
                                 NoSuchMethodException |
                                 InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Call this method from public void execute(... in order to see what it does
    private boolean isUserInChat(AbsSender absSender, long chatId, long userId) throws TelegramApiException {
        GetChatMember getChatMember = new GetChatMember();
        getChatMember.setChatId(chatId);
        getChatMember.setUserId(userId);
        ChatMember chatMember = null;
        try {
            chatMember = absSender.execute(getChatMember);
        } catch (Exception e) {
            System.err.println(e.toString());
            throw e;
        }

        System.out.println(chatMember.getUser());
        System.out.println(chatMember.getStatus());
        return chatMember.getStatus() != null;
    }


}