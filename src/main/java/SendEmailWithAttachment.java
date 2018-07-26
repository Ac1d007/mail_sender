import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class SendEmailWithAttachment {
    public static void main(String[] args) {
        SendEmailWithAttachment demo = new SendEmailWithAttachment();
        demo.sendEmail();
    }

    public void sendEmail() {
        // Defines the E-Mail information.
        String from = "email of the sender";
        String to = "email of the receiver";
        String subject = "Important Message";
        String bodyText = "This is a important message with attachment.";

        // The attachment file name.
        String attachmentName = "D:/Temp/hello.txt";

        // Creates a Session with the following properties.
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "email of the sender");
        props.put("mail.smtp.password", "");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "login of the sender", "password of the sender");// Specify the Username and the PassWord
            }});

        try {
            InternetAddress fromAddress = new InternetAddress(from);
            InternetAddress toAddress = new InternetAddress(to);

            // Create an Internet mail msg.
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(fromAddress);
            msg.setRecipient(Message.RecipientType.TO, toAddress);
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            // Set the email msg text.
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(bodyText);

            // Set the email attachment file
            FileDataSource fileDataSource = new FileDataSource(attachmentName);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setDataHandler(new DataHandler(fileDataSource));
            attachmentPart.setFileName(fileDataSource.getName());

            // Create Multipart E-Mail.
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);
            multipart.addBodyPart(attachmentPart);

            msg.setContent(multipart);

            // Send the msg. Don't forget to set the username and password
            // to authenticate to the mail server.
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}