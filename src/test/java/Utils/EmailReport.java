package Utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailReport {
    public static void sendEmailWithAttachment(String host, String port, final String userName, final String password, String[] toAddresses, String subject, String message, String attachFile) throws MessagingException, IOException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");


        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);


        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddressArray = new InternetAddress[toAddresses.length];
        for (int i = 0; i < toAddresses.length; i++) {
            toAddressArray[i] = new InternetAddress(toAddresses[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, toAddressArray);
        msg.setSubject(subject);
        msg.setSentDate(new Date());


        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");


        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);


        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile(attachFile);

        multipart.addBodyPart(attachPart);

        msg.setContent(multipart);


        Transport.send(msg);
    }
}