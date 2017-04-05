package com.example.service;

import com.example.model.Address;

import java.util.List;

public interface AddressService {

    void saveAddress(Address address);

    List<Address> findAllAddresses();

    Address findById(Long id);

    void deleteAddress(Long id);
}
