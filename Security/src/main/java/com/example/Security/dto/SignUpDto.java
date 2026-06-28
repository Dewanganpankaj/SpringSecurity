package com.example.Security.dto;

import lombok.Data;

@Data
public class SignUpDto {
    // add some check point logic for verification
    private String email;
    private String password;
    private String name;

}
