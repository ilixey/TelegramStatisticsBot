package com.telegrambotwebapp;

import com.telegrambotwebapp.bots.StatisticsBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

    public StatisticsBot statisticsBot;

    public MyTimerTask(StatisticsBot statisticsBot){
        this.statisticsBot = statisticsBot;
    }
    @Override
    // The method that is called every time the timer runs.
    public void run() {
        try {
            statisticsBot.sendStatistics();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
