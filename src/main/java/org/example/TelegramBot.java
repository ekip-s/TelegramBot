package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class TelegramBot extends TelegramLongPollingBot {

    public static final String BOT_TOKEN = "5818786828:AAFHRnVd64Kq_K1_XoccaHotWX_XOJZJK78";

    public static final String BOT_USERNAME = "robotBobot";

    public static final String URI = "https://api.nasa." +
            "gov/planetary/apod?api_key=eqeuCxifYsF27kBL1QN1N4BxoEue9sluGEf6kDIj";

    public static long chat_id;

    public TelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            chat_id = update.getMessage().getChatId();
            switch (update.getMessage().getText()) {
                case "/help":
                    sendMessage("Привет, я бот NASA! Я высылаю ссылки на картинки по запросу. " +
                            "Напоминаю, что картинки на сайте NASA обновляются раз в сутки");
                    break;
                case "/start":
                    sendMessage("Я знаю команды: \n" +
                            "/help\n" +
                            "/give");
                    break;
                case "/give":
                    try {
                        sendMessage(Utils.getUrl(URI));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    sendMessage("Я не понимаю :(");
            }
        }
    }


    private void sendMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
