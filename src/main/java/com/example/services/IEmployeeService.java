package com.example.services;

import com.example.dto.DtoEmployee;

import java.util.List;

public interface IEmployeeService {
    public List<DtoEmployee> findAllEmployees();
}
