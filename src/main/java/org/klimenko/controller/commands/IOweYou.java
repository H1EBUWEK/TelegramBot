package org.klimenko.controller.commands;


import org.klimenko.service.Calculus;
import org.klimenko.DAO;
import org.klimenko.Parser;
import org.klimenko.WrongFormatException;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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

        String controller = "credit";
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
            Calculus.AddDebt(debtor, creditor, money, chat, user, absSender, controller);
        } catch (Exception e) {
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
}












//        try {
//            if (isUserInChat(absSender, chat.getId(), DAO.GetUserId(debtor))) {
//                if (!DAO.TableListContains(chatid)) {
//                    DAO.CreateTable(chatid);
//                } else if (DAO.TableListContains(chatid)) {
//                    if (!creditor.equals(debtor)) {
//                        if (DAO.CheckTinId(user.getId())) {
//                            if (DAO.CheckTinUsername(debtor)) {
//                                try {
//                                    Calculus.AddDebt(debtor, creditor, money, chatid);
//                                } catch (SQLException | ClassNotFoundException | InvocationTargetException |
//                                         NoSuchMethodException |
//                                         InstantiationException | IllegalAccessException e) {
//                                    System.out.println(e);
//                                }
//                            } else {
//                                //вывести что пользователя нет в базе. попросите его зарегестрироваться
//                                StringBuilder UserNotRegistred = new StringBuilder();
//                                UserNotRegistred.append("There is no such user as " + debtor + " in chat\n\n");
//                                SendMessage noUserInChatMessage = new SendMessage();
//                                noUserInChatMessage.setChatId(chat.getId().toString());
//                                noUserInChatMessage.setText(UserNotRegistred.toString());
//
//                                try {
//                                    absSender.execute(noUserInChatMessage);
//                                } catch (TelegramApiException e) {
//                                    System.out.println(e);
//                                }
//                            }
//                        } else {
//                            DAO.AddToTin(Math.toIntExact(user.getId()), user.getUserName());
//                            if (DAO.CheckTinUsername(debtor)) {
//                                try {
//                                    Calculus.AddDebt(debtor, creditor, money, chatid);
//                                } catch (SQLException | ClassNotFoundException | InvocationTargetException |
//                                         NoSuchMethodException |
//                                         InstantiationException | IllegalAccessException e) {
//                                    throw new RuntimeException(e);
//                                }
//                            }
//                        }
//                    }
//                }
//            } else {
//                //вывести что пользователя нет в базе. попросите его зарегестрироваться
//                StringBuilder UserNotRegistred = new StringBuilder();
//                UserNotRegistred.append("There is no such registered user as " + creditor + " in system\n\n");
//                SendMessage noUserInChatMessage = new SendMessage();
//                noUserInChatMessage.setChatId(chat.getId().toString());
//                noUserInChatMessage.setText(UserNotRegistred.toString());
//
//                try {
//                    absSender.execute(noUserInChatMessage);
//                } catch (TelegramApiException e) {
//                    System.out.println(e);
//                }
//            }
//        } catch (SQLException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
//                 InstantiationException | IllegalAccessException | TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // Call this method from public void execute(... in order to see what it does
//    private boolean isUserInChat(AbsSender absSender, long chatId, long userId) throws TelegramApiException {
//        GetChatMember getChatMember = new GetChatMember();
//        getChatMember.setChatId(chatId);
//        getChatMember.setUserId(userId);
//        ChatMember chatMember;
//        try {
//            chatMember = absSender.execute(getChatMember);
//        } catch (Exception e) {
//            return false;
//        }
//        return chatMember.getStatus().equals("member") || chatMember.getStatus().equals("creator") || chatMember.getStatus().equals("administrator") || chatMember.getStatus().equals("restricted");
//    }