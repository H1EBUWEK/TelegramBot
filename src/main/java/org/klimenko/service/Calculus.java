package org.klimenko.service;

import org.klimenko.DAO;
import org.klimenko.Person;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Calculus {

    public static void AddDebt(String debtor, String creditor, BigDecimal money, String chatId) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (!DAO.CheckDebtLine(debtor, creditor, chatId)) {
            DAO.AddCreditLine(debtor, creditor, money, chatId);
        }else {
            BigDecimal oldMoney = DAO.getDebtBalance(debtor, creditor, chatId);
            if (oldMoney == null){
                oldMoney = DAO.getDebtBalance(creditor, debtor, chatId);
            }
            if(DAO.CheckDebtLineStraight(creditor, debtor, chatId)) {
                DAO.ChangeCreditLine(debtor, creditor, oldMoney.add(money), chatId);
            } else {
                switch (oldMoney.compareTo(money)){
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
                        DAO.AddReverseToTelegramCalculation(newMoney, debtor, creditor, chatId);
                        break;
                }
            }
        }
    }


    public static List<String> Balance(String username, String chatId) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List <Person> myBalance = DAO.BalanceTelegramCalculation(username, chatId);
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
