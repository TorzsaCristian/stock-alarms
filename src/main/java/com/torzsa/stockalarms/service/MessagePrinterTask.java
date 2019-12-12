package com.torzsa.stockalarms.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MessagePrinterTask implements Runnable{
    private String message;

    public MessagePrinterTask(String message) {
        this.message = message;
    }
    public void run() {
        System.out.println(message + " Printed by " + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}