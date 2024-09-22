package com.example.services;

import com.example.dto.DtoCustomer;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface ICustomerService {

    public DtoCustomer findCustomerById(Long id);
}
