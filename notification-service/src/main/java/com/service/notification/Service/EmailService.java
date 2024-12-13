package com.service.notification.Service;

import com.service.notification.Dto.NotificationDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Bean
    public Consumer<NotificationDto> stockVerificationEmail() {
        return message -> {
            try {
                System.out.println("Received notification :" + message.toString());
                sendNotification(message);
                System.out.println("Email sent successfully to " + message.getEmail());
            } catch (Exception e) {
                System.err.println("Failed to process email notification: " + e.getMessage());
            }

        };
    }


    public void sendNotification(NotificationDto notificationDto) throws MessagingException {

        Context context = new Context();
        context.setVariable("managerName", notificationDto.getManagerName());
        context.setVariable("productName", notificationDto.getProductName());
        context.setVariable("quantity", notificationDto.getQuantity());
        sendEmail(notificationDto.getEmail(), context,"Low Stock Alert","stock");
    }

    private void sendEmail(String to, Context context,String subject, String template) throws MessagingException {
        String content = templateEngine.process(template, context);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper email = new MimeMessageHelper(message,true);
        email.setTo(to);
        email.setSubject(subject);
        email.setText(content,true);
        mailSender.send(message);
    }
}
