package models.recurses;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Mensageiro {

 private static final String REMETENTE_EMAIL = "cadmonitores@gmail.com"; 
 
 private static final String REMETENTE_SENHA_APP = "hklp cnyp cfoc cyqy"; 

 public static void enviarEmail(String destinatario, String mensagem) {

     Properties props = new Properties();
     props.put("mail.smtp.auth", "true");
     props.put("mail.smtp.starttls.enable", "false");
     props.put("mail.smtp.host", "smtp.gmail.com");
     props.put("mail.smtp.port", "465");
     props.put("mail.smtp.socketFactory.port", "465");
     props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

     System.out.println("[Mensageiro] Conectando ao servidor de e-mail...");

     Session session = Session.getInstance(props, new Authenticator() {
         @Override
         protected PasswordAuthentication getPasswordAuthentication() {
             return new PasswordAuthentication(REMETENTE_EMAIL, REMETENTE_SENHA_APP);
         }
     });

     try {
         Message emailMessage = new MimeMessage(session);
         
         emailMessage.setFrom(new InternetAddress(REMETENTE_EMAIL));
         
         emailMessage.setRecipients(
             Message.RecipientType.TO,
             InternetAddress.parse(destinatario)
         );
         
         emailMessage.setSubject("Confirmação de Inscrição - Monitoria");
         
         emailMessage.setText(mensagem);

         System.out.println("[Mensageiro] Enviando e-mail...");
         Transport.send(emailMessage);
         System.out.println("[+] E-mail de confirmação enviado com sucesso para: " + destinatario);

     } catch (MessagingException e) {
         System.err.println("\n[!] Falha ao enviar e-mail:");
         e.printStackTrace();
         System.err.println("[!] Dica: Verifique sua conexão, o 'REMETENTE_EMAIL' e a 'REMETENTE_SENHA_APP' na classe Mensageiro.");
         System.err.println("[!] Dica II: O seu antivírus pode estar bloqueando a conexão (desative-o temporariamente).");
     }
 }
}