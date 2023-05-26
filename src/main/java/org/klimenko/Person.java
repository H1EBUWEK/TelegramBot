package org.klimenko;

import java.math.BigDecimal;

public class Person {
    public String debtor;
    public String creditor;
    public BigDecimal money;

    public Person(String debtor, String creditor, BigDecimal money){
        this.debtor = debtor;
        this.creditor = creditor;
        this.money = money;
    }

}
