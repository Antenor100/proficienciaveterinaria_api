package br.com.devantenor.clinivet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
@PropertySource("classpath:application.properties")
public class JavaMailComponent {
    @Value("${costumer.email}")
    private String email;

    @Value("${costumer.email.password}")
    private String password;

    public void sendEmailByMailTrap(String targedEmail, String assunto, String conteudo) {
        Properties props = new Properties();

        //Parâmetros de conexão com servidor Gmail
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.mailtrap.io");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.ssl.trust", false);

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        //Ativa Debug
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);

            //Remetente
            message.setFrom(new InternetAddress(email));

            //Destinatário
            Address toUser = new InternetAddress(targedEmail);
            message.setRecipient(Message.RecipientType.TO, toUser);

            message.setSubject(assunto);//Assunto

            message.setText(conteudo);//Conteudo

            //Método para enviar a mensagem criada
            Transport.send(message);

        } catch (Exception e) {
            throw new Error("Erro ao enviar e-mail automaticamente");
        }
    }
}