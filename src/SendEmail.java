import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

   static String MessageOutgoing = "";

   public static void sendMail(String recepient) throws Exception {

      System.out.println("prep to send Email");
      Properties prop = new Properties();

      prop.put("mail.smtp.auth", "true");
      prop.put("mail.smtp.starttls.enable", "true");
      prop.put("mail.smtp.host", "smtp.gmail.com");
      prop.put("mail.smtp.port", "587");

      Session sess = Session.getInstance(prop, new Authenticator() {
         @Override
         protected PasswordAuthentication getPasswordAuthentication() {
            // TODO Auto-generated method stub
            return new PasswordAuthentication(AccountInfo.Email, AccountInfo.Emalpass);

         }
      });

      Message message = prepMessage(sess, recepient);

      try {
         Transport.send(message);
      } catch (MessagingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      System.out.println("message sent success");

   }

   public void MessageToBeSent(String M) {
      MessageOutgoing = M;
   }

   private static Message prepMessage(Session session, String recp) {

      try {
         Message mess = new MimeMessage(session);
         mess.setFrom(new InternetAddress(AccountInfo.Email));
         mess.setRecipient(Message.RecipientType.TO, new InternetAddress(recp));
         mess.setSubject("Status Update from Enrollment Bot");
         mess.setText(MessageOutgoing);

         return mess;
      } catch (AddressException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (MessagingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      return null;

   }

}