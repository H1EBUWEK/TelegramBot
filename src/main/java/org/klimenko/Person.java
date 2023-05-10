package org.klimenko;

import java.math.BigDecimal;

public class Person {
    String debtor;
    String creditor;
    Double money;

    public Person(String debtor, String creditor, Double money){
        this.debtor = debtor;
        this.creditor = creditor;
        this.money = money;
    }

}
