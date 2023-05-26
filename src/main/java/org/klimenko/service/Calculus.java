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
                if(oldMoney.compareTo(money) >= 0) {
                    DAO.ChangeCreditLine(debtor, creditor, oldMoney.subtract(money));
                } else{
                    BigDecimal newMoney = money.subtract(oldMoney);
                    DAO.DeleteCreditline(creditor, debtor);
                    DAO.DeleteCreditline(debtor, creditor);
                    DAO.AddReverseToTelegramCalculation(newMoney, debtor, creditor);
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
//        Person person = new Person(debtor, creditor, money);
//        if (!personList.isEmpty()) {
//            for (Person personal : personList) {
//                if (personal.debtor.equals(debtor) && personal.creditor.equals(creditor) || personal.debtor.equals(creditor) && personal.creditor.equals(debtor)) {
//                    //some methods changing money
//                    if (personal.debtor.equals(debtor)) {
//                        personal.money += money;
//                    }
//                    if (personal.debtor.equals(creditor)) {
//                        personal.money -= money;
//                        if (personal.money < 0) {
//                            String temp = personal.debtor;
//                            personal.debtor = personal.creditor;
//                            personal.creditor = temp;
//                            personal.money *= -1;
//                        }
//                    }
//                }
//                else {
//                    personList.add(person);
//                }
//            }
//
//        } else {
//            personList.add(person);
//        }
    public static List<String> Balance(String username) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List <Person> myBalance = DAO.BalanceTelegramCalculation(username);
        List<String> allBalance = new ArrayList<>();
        String balance = " ";
        for (Person person : myBalance) {
            if (username.equals(person.debtor)) {
                balance = String.format("%s ows me %.2f", person.creditor, person.money);
            }
            if (username.equals(person.creditor)) {
                balance = String.format("I owe %s %.2f", person.debtor, person.money);
            }
            allBalance.add(balance);
        }
        return allBalance;
    }
}
