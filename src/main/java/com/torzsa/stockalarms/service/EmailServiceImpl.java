package com.torzsa.stockalarms.service;

import com.torzsa.stockalarms.model.Alarm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(List<Alarm> alarms) {
        for (Alarm alarm : alarms) {
            System.out.println("^^^ Sending email to " + alarm.getUser().getEmail());

            StringBuilder content = new StringBuilder("Hello, " + alarm.getUser().getEmail() + "!\n");
            content.append(String.format("Stock %s has reached your target value of $%s!\n", alarm.getStock().getSymbol(), alarm.getEndPrice()));


            // Send actual email
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(alarm.getUser().getEmail());
            msg.setSubject("Alarm triggered for stock " + alarm.getStock().getSymbol());
            msg.setText(content.toString());

            javaMailSender.send(msg);

        }

    }
}
