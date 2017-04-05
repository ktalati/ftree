package com.example.repository;

import com.example.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("phoneRepository")
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    Phone findByType(int type);

}
