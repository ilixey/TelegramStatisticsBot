package com.telegrambotwebapp.bots;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.telegrambotwebapp.entities.ActivityEntity;
import com.telegrambotwebapp.services.DatabaseService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class StatisticsBot extends TelegramLongPollingBot {

    DatabaseService databaseService = new DatabaseService();
    //MailService mailService = new MailService();

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

    // Creating a pdf file with all activities from database and sending it to all users who have ever sent a message to
    // the bot.
    public void sendStatistics() throws TelegramApiException, IOException {
        Document document = new Document();
        List<ActivityEntity> list = databaseService.getAllActivities();
        try{
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("test.pdf"));
            document.open();
            for (ActivityEntity activity: list){
                document.add((new Paragraph("Id: " + activity.getId() + "\n" + "Name" + activity.getName() + "\n" + "Surname: " + activity.getSurname() + "\n" + "Activity: " + activity.getActivity() + "\n" + "Duration: " + activity.getDuration() + " ??????????" + "\n" + "Date of publish" + activity.getDate() + "\n\n\n")));
            }

            document.close();
            pdfWriter.close();

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        SendDocument sendDocument = new SendDocument();
        sendDocument.setDocument(new InputFile(new File("test.pdf")));
        for (Long chatId: chatIdList){
            sendDocument.setChatId(chatId);
            execute(sendDocument);
        }

        //mailService.sendMessage();

//        StringBuilder stringBuilder = new StringBuilder();
//        for (ActivityEntity activity: list){
//            stringBuilder.append("Activity id: " + activity.getId() + "\n").append("User name: " + activity.getName() + "\n").append("User surname: " + activity.getSurname() + "\n").append("Activity: " + activity.getActivity() + "\n").append("Duration: " + activity.getDuration() + " ??????????" + "\n").append("Date: " + activity.getDate() + "\n\n\n");
//        }
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setText(stringBuilder.toString());
//        for (Long chatId: chatIdList){
//            sendMessage.setChatId(chatId);
//            execute(sendMessage);
//        }
    }

}

