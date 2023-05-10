package org.klimenko;


import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;


public class IOweYou extends BotCommand {

    public IOweYou() {
        super("ioweyou", "I owe you money");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        String creditor = user.getUserName();
        String debtor;
        double money;
        try {
            debtor = (String) Parser.ParsingMoney(strings).get("name");
            money = (double) Parser.ParsingMoney(strings).get("amount");
        } catch (WrongFormatException e) {
            throw new RuntimeException(e);
        }
        System.out.println(debtor);
        System.out.println(creditor);
        System.out.println(money);

        Calculus.Transactions(debtor, creditor, money);
    }
}