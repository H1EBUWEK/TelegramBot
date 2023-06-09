package org.klimenko.controller.commands;

import org.klimenko.DAO;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.bots.AbsSender;


public class Tester {
    public static boolean testPassingCredit (AbsSender absSender, Chat chat, String debtor, User user, String creditor, String sqlChatId) throws Exception {

        if (!isUserInChat(absSender, chat.getId(), DAO.GetUserId(debtor))) {
            throw new Exception("There is no such registered user as " + creditor + " in system\n\n");
        }
        if (!DAO.TableListContains(sqlChatId)) {
            DAO.CreateTable(sqlChatId);
        }
        if (creditor.equals(debtor)) {
            throw new Exception("Debtor and creditor can't be the same person\nPlease, check the transaction\n\n");
        }
        if (DAO.CheckTinId(user.getId())) {
            DAO.AddToTin(Math.toIntExact(user.getId()), user.getUserName());
        }
        if (!DAO.CheckTinUsername(debtor)) {
            throw new Exception("There is no such user as " + debtor + " in chat\n\n");
        }
        return true;
    }
    public static boolean testPassingDebit (AbsSender absSender, Chat chat, String debtor, User user, String creditor, String sqlChatId) throws Exception {

        if (!isUserInChat(absSender, chat.getId(), DAO.GetUserId(creditor))) {
            throw new Exception("There is no such registered user as " + creditor + " in system\n\n");
        }
        if (!DAO.TableListContains(sqlChatId)) {
            DAO.CreateTable(sqlChatId);
        }
        if (creditor.equals(debtor)) {
            throw new Exception("Debtor and creditor can't be the same person\nPlease, check the transaction\n\n");
        }
        if (DAO.CheckTinId(user.getId())) {
            DAO.AddToTin(Math.toIntExact(user.getId()), user.getUserName());
        }
        if (!DAO.CheckTinUsername(creditor)) {
            throw new Exception("There is no such user as " + debtor + " in chat\n\n");
        }
        return true;
    }

    public static boolean isUserInChat(AbsSender absSender, long chatId, long userId) {
        GetChatMember getChatMember = new GetChatMember();
        getChatMember.setChatId(chatId);
        getChatMember.setUserId(userId);
        ChatMember chatMember;
        try {
            chatMember = absSender.execute(getChatMember);
        } catch (Exception e) {
            return false;
        }
        return chatMember.getStatus().equals("member") || chatMember.getStatus().equals("creator") || chatMember.getStatus().equals("administrator") || chatMember.getStatus().equals("restricted");
    }
}
