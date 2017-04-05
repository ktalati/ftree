package com.example.service;

import com.example.model.Phone;

import java.util.List;

public interface PhoneService {

    void savePhone(Phone phone);

    List<Phone> findAllPhones();

    Phone findById(Long id);

    void deletePhone(Long id);
}
