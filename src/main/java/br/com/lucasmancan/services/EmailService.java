package br.com.lucasmancan.services;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@Log4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void send(String to, String subject, String body){
        this.send(to, subject, body, false);
    }

    public void send(String to, String subject, String body, boolean isHtml) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper( mail );
            helper.setTo( to);
            helper.setSubject( subject );
            helper.setText(body, isHtml);
            mailSender.send(mail);
        } catch (Exception e) {
            log.warn("Error on send e-mail to :"+ to );
            e.printStackTrace();
        }
    }
}
