package org.klimenko.controller.commands;

import org.klimenko.DAO;
import org.klimenko.service.Calculus;
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

public class IPaidYou extends BotCommand {
    public IPaidYou() {
        super("ipaidyou", "I paid my debts");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String creditor;
        BigDecimal money;
        String debtor = user.getUserName();
        String chatid = String.valueOf(chat.getId() > 0 ? chat.getId() : "m"+chat.getId()*(-1));
        try {
            creditor = (String) Parser.ParsingMoney(strings).get("name");
            money = (BigDecimal) Parser.ParsingMoney(strings).get("amount");
        } catch (WrongFormatException e) {
            throw new RuntimeException(e);
        }

        try {
            if (isUserInChat(absSender, chat.getId(), DAO.GetUserId(creditor))) {
                if (!DAO.TableListContains(chatid)) {
                    DAO.CreateTable(chatid);
                } else {
                    if (!creditor.equals(debtor)) {
                        if (DAO.CheckTinId(user.getId())) {
                            if (DAO.CheckTinUsername(debtor)) {
                                try {
                                    Calculus.AddDebt(debtor, creditor, money, chatid);
                                } catch (SQLException | ClassNotFoundException | InvocationTargetException |
                                         NoSuchMethodException |
                                         InstantiationException | IllegalAccessException e) {
                                    System.out.println(e);
                                }
                            } else {
                                //вывести что пользователя нет в базе. попросите его зарегестрироваться
                                StringBuilder UserNotRegistred = new StringBuilder();
                                UserNotRegistred.append("There is no such registered user as " + creditor + " in system\n\n");
                                SendMessage noUserInChatMessage = new SendMessage();
                                noUserInChatMessage.setChatId(chat.getId().toString());
                                noUserInChatMessage.setText(UserNotRegistred.toString());

                                try {
                                    absSender.execute(noUserInChatMessage);
                                } catch (TelegramApiException e) {
                                    System.out.println(e);
                                }
                            }
                        } else {
                            DAO.AddToTin(Math.toIntExact(user.getId()), user.getUserName());
                            if (DAO.CheckTinUsername(debtor)) {
                                try {
                                    Calculus.AddDebt(debtor, creditor, money, String.valueOf(chat.getId()));
                                } catch (SQLException | ClassNotFoundException | InvocationTargetException |
                                         NoSuchMethodException |
                                         InstantiationException | IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }
            } else {
                StringBuilder noUserInChatBuilder = new StringBuilder();
                noUserInChatBuilder.append("There is no such user as " + creditor + " in chat\n\n");
                SendMessage noUserInChatMessage = new SendMessage();
                noUserInChatMessage.setChatId(chat.getId().toString());
                noUserInChatMessage.setText(noUserInChatBuilder.toString());

                try {
                    absSender.execute(noUserInChatMessage);
                } catch (TelegramApiException e) {
                    System.out.println(e);
                }
            }
        } catch (TelegramApiException | SQLException | ClassNotFoundException | InvocationTargetException |
                 NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean isUserInChat(AbsSender absSender, long chatId, long userId) throws TelegramApiException {
        GetChatMember getChatMember = new GetChatMember();
        getChatMember.setChatId(chatId);
        getChatMember.setUserId(userId);
        ChatMember chatMember;
        try {
            chatMember = absSender.execute(getChatMember);
        } catch (Exception e) {
            return false;
        }


        System.out.println(chatMember.getUser());
        System.out.println(chatMember.getStatus());
        return chatMember.getStatus().equals("member");
    }
}
