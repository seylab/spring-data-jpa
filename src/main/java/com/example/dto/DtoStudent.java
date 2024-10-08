package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoStudent {

    private Integer id;
    private String firstName;
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DtoCourse> courses=new ArrayList<>();
}
