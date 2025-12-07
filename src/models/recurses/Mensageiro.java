package models.recurses;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class Mensageiro {

    private static final String REMETENTE_EMAIL = "cadmonitores@gmail.com"; 
    private static final String REMETENTE_SENHA_APP = "hklp cnyp cfoc cyqy"; 

    // Método ÚNICO e SIMPLES (Apenas texto)
    public static void enviarEmail(String destinatario, String assunto, String mensagem) throws MessagingException {
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMETENTE_EMAIL, REMETENTE_SENHA_APP);
            }
        });

        Message emailMessage = new MimeMessage(session);
        emailMessage.setFrom(new InternetAddress(REMETENTE_EMAIL));
        emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        emailMessage.setSubject(assunto);
        
        // Define o conteúdo como TEXTO PURO (Isso evita o erro de MIME/DCH)
        emailMessage.setText(mensagem);

        Transport.send(emailMessage);
        System.out.println("[Mensageiro] E-mail enviado para: " + destinatario);
    }
}