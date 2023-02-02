package com.lineate.buscompany.traderE;

import com.lineate.buscompany.errors.ServerException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;

public class Mail {
    public void sendMail(List<String> mails, String text) throws ServerException {
        for (String mail : mails) {
            String host = "smtp.mail.ru";
            String to = mail;
            String from = "!!earthquakes@list.ru";
            String user = "earthquakes@list.ru";
            String password = "4ePa47gtA5ZjvQuq0H2R";
            PasswordAuthentication auth;
            Session session;

            auth = new PasswordAuthentication(user, password);
            Properties properties = new Properties();

            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", 25);
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.ssl.trust", host);
            session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return auth;
                }
            });
            session.getDebug();
            try {
                MimeMessage message = new MimeMessage(session);

                message.setFrom(new InternetAddress(from));
                message.setSubject("Earthquakes");
                message.setText(text);
                message.addHeader("Disposition-Notification-To",to);

                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(text);

//                MimeBodyPart attachmentPart = new MimeBodyPart();
//                attachmentPart.attachFile(new File("cat.png"));
                Multipart multipart = new MimeMultipart();
//                multipart.addBodyPart(attachmentPart);
                multipart.addBodyPart(messageBodyPart);

                message.setContent(multipart);

                Transport.send(message);
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        }

    }

}
