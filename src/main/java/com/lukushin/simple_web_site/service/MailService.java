package com.lukushin.simple_web_site.service;

import com.lukushin.simple_web_site.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    public JavaMailSender javaMailSender;
    @Autowired
    public UserRepository userRepository;

    public void sendMail(String emailTo, String subject, String text){
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(mailFrom);
        smm.setTo(emailTo);
        smm.setSubject(subject);
        smm.setText(text);
        javaMailSender.send(smm);
    }
}
