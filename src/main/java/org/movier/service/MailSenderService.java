package org.movier.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    @Value("${mail.username}")
    private String from;
    private final JavaMailSender mailSender;
    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {

        System.out.println("Sending email to " + to
        + " with subject " + subject
        + " and text " + text
        +" and from " + from);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(from);
        mailSender.send(message);

    }
}
