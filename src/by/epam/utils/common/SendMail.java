package by.epam.utils.common;

import org.apache.log4j.Logger;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

    public SendMail() {
    }

    private static final Logger LOG = Logger.getLogger(SendMail.class);

    public void send(String email, String content) {
        String host = "smtp.gmail.com";
        String from = "osmnsteam@gmail.com";
        String pass = "*(FY)@#EU#@D8yu";
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        Transport transport = null;
        try {
            InternetAddress to = new InternetAddress(email);
            message.addRecipient(Message.RecipientType.TO, to);
            message.setFrom(new InternetAddress(from));
            message.setSubject("Email from OSMNS");
            message.setContent(content, "text/html");
            transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            try {
                if (transport != null)
                    transport.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
