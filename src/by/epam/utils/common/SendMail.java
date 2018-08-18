package by.epam.utils.common;

import by.epam.utils.manager.Manager;
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
        String host = Manager.getMan().getConfig("email.host");
        String from = Manager.getMan().getConfig("email.from");
        String pass = Manager.getMan().getConfig("email.pass");
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", Manager.getMan().getConfig("email.starttls.enable"));
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", Manager.getMan().getConfig("email.port"));
        props.put("mail.smtp.auth", Manager.getMan().getConfig("email.auth"));
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
            LOG.info("Email to " + email + " is processed.");
        } catch (MessagingException e) {
            LOG.debug("Email to " + email + " was not processed. Exception is thrown.");
        } finally {
            try {
                if (transport != null)
                    transport.close();
            } catch (MessagingException e) {
                LOG.debug("Can't close transport object after email processing.");
            }
        }
    }
}