package org.klimenko;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import static org.klimenko.BotConfig.BOT_NAME;
import static org.klimenko.BotConfig.BOT_TOKEN;


public class MyBot extends TelegramLongPollingCommandBot {

    MyBot(){
        register(new IOweYou());
        register(new YouOweMe());
        register(new MyBalance());
        register(new IPaidYou());
        register(new YouPaidMe());
        Help help = new Help(this);
        register(help);

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownmessage = new SendMessage();
            commandUnknownmessage.setText(message.getText());
            commandUnknownmessage.setChatId(message.getChatId());
            commandUnknownmessage.setText("The command " + commandUnknownmessage.getText() + " is unknown command. Here is some help for you: ");
            try {
                absSender.execute(commandUnknownmessage);
            } catch (TelegramApiException e) {
                System.out.println("Error" + e);
            }
            help.execute(absSender, message.getFrom(), message.getChat(), new String[]{});
            // We check if the update has a message and the message has text
        });
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage personsMessage = new SendMessage(); // Create a SendMessage object with mandatory fields
            personsMessage.setChatId(update.getMessage().getChatId().toString());
            personsMessage.setText(update.getMessage().getText());

            try {
                execute(personsMessage); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}