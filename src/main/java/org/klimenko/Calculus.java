package org.klimenko;

import java.util.ArrayList;
import java.util.List;

import static org.klimenko.Main.personList;

public class Calculus {

    public static void Transactions(String debtor, String creditor, Double money) {
        Person person = new Person(debtor, creditor, money);
        if (!personList.isEmpty()) {
            for (Person personal : personList) {
                if (personal.debtor.equals(debtor) && personal.creditor.equals(creditor) || personal.debtor.equals(creditor) && personal.creditor.equals(debtor)) {
                    //some methods changing money
                    if (personal.debtor.equals(debtor)) {
                        personal.money += money;
                    }
                    if (personal.debtor.equals(creditor)) {
                        personal.money -= money;
                        if (personal.money < 0) {
                            String temp = personal.debtor;
                            personal.debtor = personal.creditor;
                            personal.creditor = temp;
                            personal.money *= -1;
                        }
                    }
                }
                else {
                    personList.add(person);
                }
            }

        } else {
            personList.add(person);
        }
    }

    public static List<String> Balance(String username) {
        List<String> allBalance = new ArrayList<>();
        String balance = " ";
        for (Person person : personList) {
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
