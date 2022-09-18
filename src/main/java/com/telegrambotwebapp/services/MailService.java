package com.telegrambotwebapp.services;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailService {

    final String destination = "rubanovasneg@gmail.com";
    final String from = "vaspiakou@gamil.com";

    final String username = "vaspiakou@gmail.com";
    final String password = "sacHew-wyfhi0-fyrmug";

    String host = "192.168.56.1";

    public void sendMessage(){
        Properties properties = new Properties();
        properties.put("mail.stmp.auth", "true");
        properties.put("mail.stmp.starttls.enable", "true");
        properties.put("mail.stmp.host", host);
        properties.put("mail.stmp.port", "25");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));
            message.setSubject("Test");
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText("Test message");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            bodyPart = new MimeBodyPart();
            String filename = "test1.pdf";
            DataSource source = new FileDataSource(filename);
            bodyPart.setDataHandler(new DataHandler(source));
            bodyPart.setFileName(filename);
            multipart.addBodyPart(bodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
