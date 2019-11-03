package br.com.lucasmancan.services;

import br.com.lucasmancan.models.MessageTemplate;
import br.com.lucasmancan.models.Token;
import br.com.lucasmancan.repositories.MessageTemplateRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.internet.MimeMessage;

@Service
@Log4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageTemplateRepository messageTemplateRepository;

    public void send(String to, String subject, String body){
        this.send(to, subject, body, false);
    }

    public void send(String template, String to, Token token){
        String subjectString = null;
        String bodyString = null;
        var messageTemplates = messageTemplateRepository.findByName(template);

        if(CollectionUtils.isEmpty(messageTemplates)){
           return;
        }

        MessageTemplate subject = messageTemplates.stream().filter(x -> !x.getIsHtml()).findFirst().orElse(null);

        MessageTemplate body = messageTemplates.stream().filter(MessageTemplate::getIsHtml).findFirst().orElse(null);


        if(subject != null){
             subjectString = subject.getValue();
        }

        if(body != null){
            bodyString = String.format(body.getValue(), token.getToken());
        }

        this.send(to, subjectString, bodyString, true);
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
