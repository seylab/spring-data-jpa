package com.example.services;

import com.example.dto.DtoAddress;

public interface IAddressService {
    public DtoAddress findAddressById(Long id);
}
