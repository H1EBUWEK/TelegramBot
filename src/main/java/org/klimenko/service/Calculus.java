package org.klimenko.service;

import org.klimenko.DAO;
import org.klimenko.Person;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Calculus {

    public static void TransactionsPlus(String debtor, String creditor, BigDecimal money) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (!DAO.CheckDebtLine(debtor, creditor)) {
            DAO.AddCreditLine(debtor, creditor, money);
        } else {
            BigDecimal oldMoney = DAO.getDebtBalance(debtor, creditor);
            System.out.println(oldMoney);
            if (oldMoney.compareTo(money) >= 0) {
                System.out.println(oldMoney.subtract(money));
                DAO.ChangeCreditLine(debtor, creditor, oldMoney.subtract(money));
            }
            if (oldMoney.compareTo(money) < 0) {
                BigDecimal newMoney = money.subtract(oldMoney);
                DAO.DeleteCreditline(creditor, debtor);
                DAO.DeleteCreditline(debtor, creditor);
                DAO.AddToTelegramCalculation(newMoney, debtor, creditor);
            }
        }
    }
    public static void AddDebt(String debtor, String creditor, BigDecimal money) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (!DAO.CheckDebtLine(debtor, creditor)) {
            DAO.AddCreditLine(debtor, creditor, money);
        }else {
            BigDecimal oldMoney = DAO.getDebtBalance(debtor, creditor);
            if (oldMoney == null){
                oldMoney = DAO.getDebtBalance(creditor, debtor);
            }
            if(DAO.CheckDebtLineStraight(creditor, debtor)) {
                DAO.ChangeCreditLine(debtor, creditor, oldMoney.add(money));
            } else {
                switch (oldMoney.compareTo(money)){
                    case 1:
                        DAO.ChangeCreditLine(creditor, debtor, oldMoney.subtract(money));
                        break;
                    case 0:
                        DAO.DeleteCreditline(creditor, debtor);
                        DAO.DeleteCreditline(debtor, creditor);
                        break;
                    case -1:
                        BigDecimal newMoney = money.subtract(oldMoney);
                        DAO.DeleteCreditline(creditor, debtor);
                        DAO.DeleteCreditline(debtor, creditor);
                        DAO.AddReverseToTelegramCalculation(newMoney, debtor, creditor);
                        break;
                }
            }
        }
    }


    public static void TransactionsMinus(String debtor, String creditor, BigDecimal money) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (!DAO.CheckDebtLine(debtor, creditor)) {
            DAO.AddCreditLine(debtor, creditor, money);
        } else {
            BigDecimal oldMoney = DAO.getDebtBalance(debtor, creditor);
            System.out.println(oldMoney);
            if (oldMoney.compareTo(money) >= 0) {
                System.out.println(oldMoney.subtract(money));
                DAO.ChangeCreditLine(debtor, creditor, oldMoney.subtract(money));
            }
            if (oldMoney.compareTo(money) < 0) {
                BigDecimal newMoney = money.subtract(oldMoney);
                DAO.DeleteCreditline(creditor, debtor);
                DAO.AddToTelegramCalculation(newMoney, creditor, debtor);
            }
        }
    }

    public static List<String> Balance(String username) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List <Person> myBalance = DAO.BalanceTelegramCalculation(username);
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
