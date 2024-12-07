package org.example;

import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Store;

import java.util.Properties;

class User {
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void listTasks() {
        Properties props = new Properties();
        props.put("mail.imap.host", "imap.gmail.com"); // Configure IMAP server
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true");

        Session session = Session.getInstance(props);

        try {
            Store store = session.getStore("imap");
            store.connect(email, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            for (Message message : messages) {
                if (message.getSubject().startsWith("New Task Assigned")) {
                    System.out.println("Task Email Found:");
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("Content: " + message.getContent().toString());
                    System.out.println("--------------------");
                }
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}