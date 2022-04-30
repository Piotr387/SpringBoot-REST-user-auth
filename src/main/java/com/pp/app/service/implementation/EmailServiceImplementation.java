package com.pp.app.service.implementation;

import com.pp.app.service.EmailService;
import com.pp.app.ui.shared.DTO.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImplementation implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    final String FROM = "ptrpiw.dev@gmail.com";
    final String SUBJECT = "One last step to complete your registration";
    final String HTML_BODY = "<h1>Please verify you email address</h1>" +
            "<p>Thank you for registering. To complte registration process and be able to log in</p>" +
            " CLick the following link: " +
            "<a href='http://localhost:8080/spring-boot-edu/email-verification-page?token=$tokenValue'>" +
            "Final step to complete your registration</a>" +
            "<br/><br/>" +
            "Thank you! And we are waiting for you!";

//    final String TEXT_BODY = "Please verify you email address. " +
//            " Thank you for registering. To complte registration process and be able to log in" +
//            " CLick the following link: " +
//            " Open the following link in your browser: " +
//            " http://localhost:8080/spring-boot-edu/email-verification-page?token=$tokenValue" +
//            " Final step to complete your registration" +
//            " Thank you! And we are waiting for you!";

    @Override
    public void sendEmail(UserDto userDto) {
        String htmlBodyWithToken = HTML_BODY.replace("$tokenValue", userDto.getEmailVerificationToken());
        //String textBodyWithToken = TEXT_BODY.replace("$tokenValue", userDto.getEmailVerificationToken());

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(userDto.getEmail());
        msg.setSubject(SUBJECT);
        msg.setText(htmlBodyWithToken);
        javaMailSender.send(msg);
    }
}
