package org.tuto;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailSender {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        final String username = "mehdirajawi239@gmail.com";
        final String password = "ztzf kapd alhk tnmu";
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mehdirajawi239@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse("mohammed2021brainy@gmail.com")
            );
            message.setSubject("JavaMail test");
            message.setText("Bonjour, tout le monde.");

            Transport.send(message);
            System.out.println("E-mail envoyé evec succès");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
