package com.example.flexisaf.service.notification;

import com.example.flexisaf.service.notification.model.EmailModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import java.nio.charset.StandardCharsets;

/**
 * Damilare
 * 23/11/2021
 **/
@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    private Configuration freemarkerConfig;

    public EmailService(JavaMailSender javaMailSender, Configuration freemarkerConfig) {
        this.javaMailSender = javaMailSender;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Value("${spring.mail.username}")
    private String emailSentFrom;

    public void sendEmail(EmailModel emailRequest) throws Exception {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

        Template t = freemarkerConfig.getTemplate(emailRequest.getTemplate());

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, emailRequest.getModel());


        helper.setTo(emailRequest.getRecipients());
        helper.setSubject(emailRequest.getSubject());
        helper.setText(html, true);
        helper.setFrom(emailSentFrom);

        javaMailSender.send(message);

    }
}
