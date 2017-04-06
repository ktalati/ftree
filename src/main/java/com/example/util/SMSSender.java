package com.example.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SMSSender {

    public boolean send(String message, List<String> phoneNumbers){
        try{
            // Try to send Message
            System.out.println("Message ::: "+message);
            for (String number:
                 phoneNumbers) {
                System.out.println("Numbers :::" + number);
            }
            return Boolean.TRUE;
        }catch (Exception e){
            // Exception handled
            return Boolean.FALSE;
        }
    }
}
