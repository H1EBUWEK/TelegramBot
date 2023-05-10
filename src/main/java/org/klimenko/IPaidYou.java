package org.klimenko;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class IPaidYou extends BotCommand {
    public IPaidYou() {
        super("ipaidyou", "I paid my debts");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String creditor;
        double money;
        String debtor = user.getUserName();
        try {
            creditor = (String) Parser.ParsingMoney(strings).get("name");
            money = (double) Parser.ParsingMoney(strings).get("amount");
        } catch (WrongFormatException e) {
            // send a message of failure and return
            SendMessage sendMessage = new SendMessage();
            return;
        }

        System.out.println(creditor);
        System.out.println(debtor);
        System.out.println(money);

        Calculus.Transactions(debtor, creditor, money);
    }
}
