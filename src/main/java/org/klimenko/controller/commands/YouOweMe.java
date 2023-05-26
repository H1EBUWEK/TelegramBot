package org.klimenko.controller.commands;


import org.klimenko.service.Calculus;
import org.klimenko.Parser;
import org.klimenko.WrongFormatException;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class YouOweMe extends BotCommand {

    public YouOweMe() {
        super("youoweme", "You owe me money");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        String debtor = user.getUserName();
        String creditor;
        BigDecimal money;
        try {
            creditor = (String) Parser.ParsingMoney(strings).get("name");
            money = (BigDecimal) Parser.ParsingMoney(strings).get("amount");
        } catch (WrongFormatException e) {
            throw new RuntimeException(e);
        }


        System.out.println(creditor);
        System.out.println(debtor);
        System.out.println(money);

        try {
            Calculus.TransactionsPlus(debtor, creditor, money);
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}