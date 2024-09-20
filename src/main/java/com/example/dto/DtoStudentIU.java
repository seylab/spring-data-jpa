package com.example.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoStudentIU {

    @NotEmpty(message = "The firstname can not be empty!")
    @Min(value = 3 , message = "The firstname must be greater than or equal to 3")
    @Max(value = 10 , message = "The firstname must be less than or equal to 10")
    private String firstName;

    @Size(min = 3 , max= 30)
    private String lastName;

    private Date birthOfDate;

    @Email(message = "The email must be a well-formed email address")
    private String email;

    @Size(min = 11 , max= 11, message="The tckn can not be empty!")
    @NotEmpty(message = "The tckn can not be empty!")
    private String tckn;

}
