package org.klimenko.controller.commands;


import org.klimenko.service.Calculus;
import org.klimenko.Parser;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;

public class YouOweMe extends BotCommand {

    public YouOweMe() {
        super("yom", "You owe me money");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        String controller = "debit";
        String debtor = user.getUserName();
        String creditor;
        BigDecimal money;
        String command = "/yom";

        try {
            creditor = (String) Parser.ParsingMoney(strings, command).get("name");
            money = (BigDecimal) Parser.ParsingMoney(strings, command).get("amount");
            Calculus.AddDebt(debtor, creditor, money, chat, user, absSender, controller);
        } catch (Exception e) {
            sendExceptionMessage(e, absSender, chat);
        }


    }
    private void sendExceptionMessage(Exception e, AbsSender absSender, Chat chat){
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