package org.tuto;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.search.ComparisonTerm;
import jakarta.mail.search.ReceivedDateTerm;
import jakarta.mail.search.SearchTerm;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailReceiver {
    // Constantes pour l'email et le mot de passe d'application
    private static final String EMAIL = "mehdirajawi239@gmail.com";
    private static final String APP_PASSWORD = "ztzf kapd alhk tnmu";

    public static void main(String[] args) {
        try (Store store = getStore(); Folder folder = openFolder(store, "INBOX")) {
            // Recherche des messages reçus dans les dernières 24 heures
            SearchTerm searchTerm = new ReceivedDateTerm(ComparisonTerm.GT, new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
            // Recherche des messages
            Message[] messages = folder.search(searchTerm);
            FetchProfile fetchProfile = new FetchProfile();
            // Charge uniquement l'enveloppe des messages (en-tête)
            fetchProfile.add(FetchProfile.Item.ENVELOPE);
            // Récupère les messages avec les informations spécifiées
            folder.fetch(messages, fetchProfile);

            // Affichage des messages récupérés
            for (Message message : messages) {
                printMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour se connecter à la boîte de réception en utilisant IMAP
    private static Store getStore() throws MessagingException {
        Properties props = new Properties();
        // Configuration des propriétés IMAP pour la connexion à Gmail
        props.put("mail.imaps.host", "imap.gmail.com");
        // Accepte le certificat SSL de Gmail
        props.put("mail.imaps.ssl.trust", "imap.gmail.com");
        // Port IMAPS pour une connexion sécurisée
        props.put("mail.imaps.port", "993");
        // Temps d'attente pour la connexion (10 secondes)
        props.put("mail.imaps.connectiontimeout", "10000");
        // Temps d'attente pour les opérations IMAP (10 secondes)
        props.put("mail.imaps.timeout", "10000");

        // Crée une session avec les propriétés spécifiées
        Session session = Session.getInstance(props);
        // Récupère l'instance de store IMAPS
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", EMAIL, APP_PASSWORD);
        // Connexion au serveur IMAP de Gmail avec l'email et mot de passe d'application
        return store;
    }

    // Méthode pour ouvrir un dossier spécifique du store IMAP (ici, la boîte de réception "INBOX")
    private static Folder openFolder(Store store, String folderName) throws MessagingException {
        Folder folder = store.getFolder(folderName);
        folder.open(Folder.READ_ONLY); // Ouvre le dossier en lecture seule
        return folder;
    }

    // Méthode pour afficher les infos d'un message (date de réception, expéditeur, sujet, et contenu)
    private static void printMessage(Message message) throws MessagingException, IOException {
        // Affiche les informations du message
        System.out.printf("RECEIVED ON: %s%nFROM: %s%nSUBJECT: %s%nTEXT: %s%n%n",
                message.getReceivedDate(),        // Date de réception
                ((InternetAddress) message.getFrom()[0]).getAddress(), // Adresse de l'expéditeur
                message.getSubject(),            // Sujet du message
                getMessageContent(message));     // Contenu du message (texte)
    }

    // Méthode récursive pour récupérer le contenu d'un message
    private static String getMessageContent(Part part) throws MessagingException, IOException, IOException {
        // Si la partie du message est de type texte brut, retourne son contenu
        if (part.isMimeType("text/plain")) {
            return (String) part.getContent();
        }
        // Si le message est multipart (par exemple, contenant plusieurs parties comme texte + images)
        else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent(); // Récupère le contenu multipart
            StringBuilder content = new StringBuilder();
            // Parcourt toutes les parties du multipart et ajoute leur contenu
            for (int i = 0; i < multipart.getCount(); i++) {
                content.append(getMessageContent(multipart.getBodyPart(i)));
            }
            return content.toString();
        }
        // Si le type MIME est inconnu, retourne une chaîne vide
        return "";
    }

}
