package com.service.notification.Service;

import com.service.notification.Dto.NotificationDto;
import com.service.notification.Dto.RequestDecisionDto;
import com.service.notification.Dto.RequestDto;
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
                sendNotification(message);
                System.out.println("Email sent successfully to " + message.getEmail());
            } catch (Exception e) {
                System.err.println("Failed to process email notification: " + e.getMessage());
            }

        };
    }

    @Bean
    public Consumer<RequestDto> productsRequestEmail() {
        return message -> {
            try {
                sendNotification(message);
                System.out.println("Email sent successfully to " + message.getTo());
            } catch (Exception e) {
                System.err.println("Failed to process email notification: " + e.getMessage());
            }

        };
    }

    @Bean
    public Consumer<RequestDecisionDto> productsRequestDecisionEmail() {
        return message -> {
            try {
                sendNotification(message);
                System.out.println("Email sent successfully to " + message.getEmail());
            } catch (Exception e) {
                System.err.println("Failed to process email notification: " + e.getMessage());
            }

        };
    }


    public void sendNotification(NotificationDto notificationDto) throws MessagingException {

        Context context = new Context();
        context.setVariable("productName", notificationDto.getProductName());
        context.setVariable("quantity", notificationDto.getQuantity());
        sendEmail(notificationDto.getEmail(), context,"Low Stock Alert","stock");
    }

    public void sendNotification(RequestDto requestDto) throws MessagingException {

        Context context = new Context();
        context.setVariable("productName", requestDto.getProductName());
        context.setVariable("id", requestDto.getIdProductsRequest());
        context.setVariable("quantity", requestDto.getQuantity());
        context.setVariable("from", requestDto.getFrom());
        sendEmail(requestDto.getTo(), context,"Request for Product Shipment","request");
    }

    public void sendNotification(RequestDecisionDto requestDecisionDto) throws MessagingException {

        Context context = new Context();
        context.setVariable("status", requestDecisionDto.getStatus());
        sendEmail(requestDecisionDto.getEmail(), context,"Request "+requestDecisionDto.getStatus(),"requestDecision");
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
