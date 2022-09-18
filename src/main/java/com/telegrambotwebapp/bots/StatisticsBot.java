package com.telegrambotwebapp.bots;

import com.telegrambotwebapp.services.DatabaseService;
import com.telegrambotwebapp.entities.ActivityEntity;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class StatisticsBot extends TelegramLongPollingBot {

    DatabaseService databaseService = new DatabaseService();

    private List<Long> chatIdList = new LinkedList<>();

    @Override
    public String getBotToken() {
        return "5633372800:AAFB9mTOsxBaB1FYU9cnD8rW4My8YBfYSvY";
    }

    @Override
    public String getBotUsername() {
        return "Green_cheloveki_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!chatIdList.contains(update.getMessage().getChatId())){
            chatIdList.add(update.getMessage().getChatId());
        }
    }

    public void sendStatistics() throws TelegramApiException, IOException {
        List<ActivityEntity> list = databaseService.getAllActivities();
        StringBuilder stringBuilder = new StringBuilder();
        for (ActivityEntity activity: list){
            stringBuilder.append("Activity id: " + activity.getId() + "\n").append("User name: " + activity.getName() + "\n").append("User surname: " + activity.getSurname() + "\n").append("Activity: " + activity.getActivity() + "\n").append("Duration: " + activity.getDuration() + " часов" + "\n").append("Date: " + activity.getDate() + "\n\n\n");
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(stringBuilder.toString());
        for (Long chatId: chatIdList){
            sendMessage.setChatId(chatId);
            execute(sendMessage);
        }
    }

}

