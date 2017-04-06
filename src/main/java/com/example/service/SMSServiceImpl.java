package com.example.service;

import com.example.model.SMS;
import com.example.repository.SMSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("smsService")
public class SMSServiceImpl implements SMSService {

    @Autowired
    private SMSRepository smsRepository;

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public void sendSMS(SMS sms) {
        smsRepository.save(sms);
    }
}