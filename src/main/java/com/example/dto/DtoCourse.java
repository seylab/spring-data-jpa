package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoCourse {
    private Long id;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DtoStudent> students = new ArrayList<>();
}
