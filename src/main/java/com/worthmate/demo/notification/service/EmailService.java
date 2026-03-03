package com.worthmate.demo.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(TemplateEngine templateEngine, JavaMailSender mailSender) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
    }

    // =========================
    // SEND SIMPLE EMAIL
    // =========================
    private void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    // =========================
    // WELCOME EMAIL (SIGNUP)
    // =========================
    public void sendWelcomeEmail(String to, String name) {
        String body = "Hi " + name + ",\n\nYour account has been successfully created! Welcome aboard.";
        sendMail(to, "Welcome to WorthMate.ai", body);
    }

    // =========================
    // BOOKING EMAIL
    // =========================
    public void sendBookingEmail(String to, String name, String topic, String date) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("topic", topic);
        context.setVariable("date", date);

        String body = templateEngine.process("emails/booking-email", context);
        sendMail(to, "Consultation Booked", body);
    }

    // =========================
    // PAYMENT EMAIL
    // =========================
    public void sendPaymentEmail(String to, String mentorName, String date) {
        Context context = new Context();
        context.setVariable("mentorName", mentorName);
        context.setVariable("date", date);

        String body = templateEngine.process("emails/payment-email", context);
        sendMail(to, "Payment Received", body);
    }

    // =========================
    // COMPLETION EMAIL
    // =========================
    public void sendCompletionEmail(String to, String studentName) {
        Context context = new Context();
        context.setVariable("studentName", studentName);

        String body = templateEngine.process("emails/completion-email", context);
        sendMail(to, "Session Completed", body);
    }

    // =========================
    // FEEDBACK EMAIL
    // =========================
    public void sendFeedbackEmail(String to, String rating) {
        Context context = new Context();
        context.setVariable("rating", rating);

        String body = templateEngine.process("emails/feedback-email", context);
        sendMail(to, "New Feedback Received", body);
    }

    // =========================
    // DEBUG PRINT (Optional)
    // =========================
    public void printEmail(String to, String subject, String body) {
        System.out.println("=================================");
        System.out.println("TO: " + to);
        System.out.println("SUBJECT: " + subject);
        System.out.println(body);
        System.out.println("=================================");
    }
}