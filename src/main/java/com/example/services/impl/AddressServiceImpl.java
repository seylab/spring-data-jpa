package com.example.services.impl;

import com.example.dto.DtoAddress;
import com.example.dto.DtoCustomer;
import com.example.entities.Address;
import com.example.repository.AddressRepository;
import com.example.services.IAddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public DtoAddress findAddressById(Long id) {

        DtoAddress dtoAddress = new DtoAddress();
        DtoCustomer dtoCustomer = new DtoCustomer();

        Optional<Address> optional = addressRepository.findById(id);
        if(optional.isEmpty()){
            return null;
        }
        Address address = optional.get();
        BeanUtils.copyProperties(address, dtoAddress);

        dtoCustomer.setId(address.getCustomer().getId());
        dtoCustomer.setName(address.getCustomer().getName());

//        dtoCustomer.setAddress(dtoAddress); isteği sonsuz döngüye sokuyor.

        dtoAddress.setCustomer(dtoCustomer);

        return dtoAddress;
    }
}
