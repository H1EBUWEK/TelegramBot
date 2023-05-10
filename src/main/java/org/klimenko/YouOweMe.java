package org.klimenko;


import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class YouOweMe extends BotCommand {

    public YouOweMe() {
        super("youoweme", "You owe me money");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        String debtor = user.getUserName();
        String creditor;
        double money;
        try {
            creditor = (String) Parser.ParsingMoney(strings).get("name");
            money = (double) Parser.ParsingMoney(strings).get("amount");
        } catch (WrongFormatException e) {
            throw new RuntimeException(e);
        }


        System.out.println(creditor);
        System.out.println(debtor);
        System.out.println(money);

        Calculus.Transactions(debtor, creditor, money);
    }
}