package main.java;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyAmazingBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        // TODO
        Message incMessage = update.getMessage();
        long chat_id = incMessage.getChatId();

        if (incMessage.getText().contains("совет") || incMessage.getText().contains("Совет")
                || incMessage.getText().contains("/advice")) {
            System.out.println("Человек просит совет");

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chat_id));
            sendMessage.setText("Подожди, человек. Я придумываю тебе совет");

            try {
                execute(sendMessage); // Sending our incMessage object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            Configuration.headless = true;
            Selenide.open("https://fucking-great-advice.ru/");
            SelenideElement selenideElement =
                    Selenide.$x("//*[@class='advice__link']").shouldBe(Condition.visible);
            String adviceText = selenideElement.getText();

            SendMessage sendMessage2 = new SendMessage();
            sendMessage2.setChatId(String.valueOf(chat_id));
            sendMessage2.setText(adviceText);

            try {
                execute(sendMessage2); // Sending our incMessage object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (incMessage.getText().contains("/author")){
            SendMessage sendMessage2 = new SendMessage();
            sendMessage2.setChatId(String.valueOf(chat_id));
            sendMessage2.setText("By Valeria Reshetina with all my love :)");

            try {
                execute(sendMessage2); // Sending our incMessage object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        // TODO
        return "amazing_lvushkin_bot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "5959929051:AAEXjeBJU_hW7WcvgW2_97QHPCxVWEzkmC0";
    }
}
