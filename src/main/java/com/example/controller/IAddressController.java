package com.example.controller;

import com.example.dto.DtoAddress;

public interface IAddressController {
    public DtoAddress findAddressById(Long id);
}
