package org.klimenko;


import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

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
        System.out.println(debtor);
        System.out.println(creditor);
        System.out.println(money);

        try {
            if (DAO.CheckTinId(Math.toIntExact(user.getId()))) {
                if (DAO.CheckTinUsername(debtor)) {
                    try {
                        Calculus.Transactions(debtor, creditor, money);
                    } catch (SQLException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                             InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    //вывести что пользователя нет в базе. попросите его зарегестрироваться
                }
            }
            else{
                DAO.AddToTin(Math.toIntExact(user.getId()), user.getUserName());
            }
        } catch (SQLException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}