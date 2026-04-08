package com.hospital.service;


import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    JavaMailSenderImpl mailSender;

    @PostConstruct
    public void initialize(){
        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.ethereal.email");
        mailSender.setPort(587);
        mailSender.setUsername("katrina.smitham52@ethereal.email");
        mailSender.setPassword("af2pdgKxf7ewg9k1wW");
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");

    }

    public void sendOtpMail(String email,String otp) {
        String activationEmail = """
            <html>
                <body>
                    <h1>${title}</h1>
                    <p>${otp}</p>
                </body>
            </html>
            """;

        var mailBody = activationEmail
                .replace("${title}", "Your otp")
                .replace("${OTP}", otp);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            message.setFrom("hospital@mail.com");
            message.setTo(email);
            message.setSubject("Activate");
            message.setText(mailBody, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        this.mailSender.send(mimeMessage);
    }



    public void sendActivationEmail(String email, String activationToken) {
        var activationUrl =  "http://localhost:8080/activation/" + activationToken;
        String activationEmail = """
            <html>
                <body>
                    <h1>${title}</h1>
                    <a href="${url}">${clickHere}</a>
                </body>
            </html>
            """;

        var mailBody = activationEmail
                .replace("${url}", activationUrl)
                .replace("${title}", "Activate user")
                .replace("${clickHere}", "Click here");

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            message.setFrom("hospital@mail.com");
            message.setTo(email);
            message.setSubject("Activate");
            message.setText(mailBody, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        this.mailSender.send(mimeMessage);
    }
}
