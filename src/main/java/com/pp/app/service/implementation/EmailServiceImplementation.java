package com.pp.app.service.implementation;

import com.pp.app.service.EmailService;
import com.pp.app.ui.shared.DTO.UserDto;
import org.springframework.stereotype.Service;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

@Service
public class EmailServiceImplementation implements EmailService {


    final String FROM = "ptrpiw.dev@gmail.com";

//    final String HTML_BODY = "<h1>Please verify you email address</h1>" +
//            "<p>Thank you for registering. To complte registration process and be able to log in</p>" +
//            " CLick the following link: " +
//            "<a href='http://localhost:8080/spring-boot-edu/email-verification-page?token=$tokenValue'>" +
//            "Final step to complete your registration</a>" +
//            "<br/><br/>" +
//            "Thank you! And we are waiting for you!";

    final String SUBJECT_EMAIL_VERIFICATION = "One last step to complete your registration\n";
    final String TEXT_BODY_EMAIL_VERIFICATION = "Please verify you email address.\n " +
            " Thank you for registering. To complte registration process and be able to log in\n" +
            " CLick the following link: \n" +
            " Open the following link in your browser: \n" +
            " http://localhost:8080/spring-boot-edu/email-verification-page?token=$tokenValue\n" +
            " Final step to complete your registration\n" +
            " Thank you! And we are waiting for you!\n";

    final String SUBJECT_PASSWORD_RESET = "Password reset\n";
    final String TEXT_BODY_PASSWORD_RESET = "A request to reset your password\n " +
            " Someone has requested to reset your password with our project. In case it wasn't you ignore it\n" +
            " Open the following link in your browser: \n" +
            " http://localhost:8080/spring-boot-edu/password-reset?token=$tokenValue\n" +
            " Thank you!\n";

    @Override
    public void sendEmail(UserDto userDto) {
        //String htmlBodyWithToken = HTML_BODY.replace("$tokenValue", userDto.getEmailVerificationToken());
        String textBodyWithToken = TEXT_BODY_EMAIL_VERIFICATION.replace("$tokenValue", userDto.getEmailVerificationToken());
        sendEmailViaLocalServer(userDto.getEmail(), SUBJECT_EMAIL_VERIFICATION ,textBodyWithToken);
    }

    @Override
    public boolean sendPasswordResetEmail(String firstName, String email, String token) {
        String textBodyWithToken = TEXT_BODY_PASSWORD_RESET.replace("$tokenValue", token);
        return sendEmailViaLocalServer(email, SUBJECT_PASSWORD_RESET, textBodyWithToken);
    }

    private boolean sendEmailViaLocalServer(String email, String subject, String messageText){
        // Assuming you are sending email from localhost
        String host = "localhost";
        // Assuming you are sending email from localhost.
        String port = "587";
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        // Setup mail server.
        properties.setProperty("mail.smtp.port", port);
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(FROM));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // Set Subject: header field
            message.setSubject(subject);
            // Now set the actual message
            message.setText(messageText);
            // Send message
            Transport.send(message);
            System.out.println("Message sent successfully....");

            return true;
        }catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }
}
