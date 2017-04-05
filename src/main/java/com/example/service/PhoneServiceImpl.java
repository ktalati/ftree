package com.example.service;

import com.example.model.Phone;
import com.example.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("phoneService")
public class PhoneServiceImpl implements PhoneService{

    @Autowired
    private PhoneRepository phoneRepository;


    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void savePhone(Phone phone) {
        phoneRepository.save(phone);
    }

    @Override
    public List<Phone> findAllPhones() {
        return phoneRepository.findAll();
    }

    @Override
    public Phone findById(Long id) {
        return phoneRepository.findOne(id);
    }

    @Override
    public void deletePhone(Long id) {
        phoneRepository.delete(id);
    }
}