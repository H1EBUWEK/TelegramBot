package org.klimenko.service;

import org.klimenko.DAO;
import org.klimenko.Person;
import org.klimenko.controller.commands.Tester;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Calculus {

    public static void AddDebt(String debtor, String creditor, BigDecimal money, Chat chat, User user, AbsSender absSender, String controller) throws Exception {

        String chatId = String.valueOf(chat.getId() > 0 ? chat.getId() : "m" + chat.getId() * (-1));

        if (controller.equals("credit")) {
            try {
                Tester.testPassingCredit(absSender, chat, debtor, user, creditor, chatId);
            } catch (Exception e) {
                throw e;
            }
        }
        if (controller.equals("debit")) {
            try {
                Tester.testPassingDebit(absSender, chat, debtor, user, creditor, chatId);
            } catch (Exception e) {
                throw e;
            }
        }
        if (!DAO.CheckDebtLine(debtor, creditor, chatId)) {
            DAO.AddCreditLine(debtor, creditor, money, chatId);
            return;
        }
        BigDecimal oldMoney = DAO.getDebtBalance(debtor, creditor, chatId);
        if (oldMoney == null) {
            oldMoney = DAO.getDebtBalance(creditor, debtor, chatId);
        }
        if (DAO.CheckDebtLineStraight(creditor, debtor, chatId)) {
            DAO.ChangeCreditLine(debtor, creditor, oldMoney.add(money), chatId);
        } else {
            switch (oldMoney.compareTo(money)) {
                case 1:
                    DAO.ChangeCreditLine(creditor, debtor, oldMoney.subtract(money), chatId);
                    break;
                case 0:
                    DAO.DeleteCreditline(creditor, debtor, chatId);
                    DAO.DeleteCreditline(debtor, creditor, chatId);
                    break;
                case -1:
                    BigDecimal newMoney = money.subtract(oldMoney);
                    DAO.DeleteCreditline(creditor, debtor, chatId);
                    DAO.DeleteCreditline(debtor, creditor, chatId);
                    DAO.AddDebtLine(newMoney, debtor, creditor, chatId);
                    break;
            }
        }
    }


    public static List<String> Balance(String username, String chatId) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Person> myBalance = DAO.Balance(username, chatId);
        List<String> allBalance = new ArrayList<>();
        String balance = " ";
        for (Person person : myBalance) {
            if (username.equals(person.debtor)) {
                balance = String.format("%s ows you %.2f", person.creditor, person.money);
            }
            if (username.equals(person.creditor)) {
                balance = String.format("You owe %s %.2f", person.debtor, person.money);
            }
            allBalance.add(balance);
        }
        return allBalance;
    }
}
