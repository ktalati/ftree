package com.example.repository;

import com.example.model.SMS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("smsRepository")
public interface SMSRepository extends JpaRepository<SMS, Long> {

}
