package org.klimenko;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class WrongFormatException extends Exception {
    public WrongFormatException(String s) {
        super(s);
    }
}
