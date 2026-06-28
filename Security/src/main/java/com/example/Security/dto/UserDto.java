package com.example.Security.dto;

import lombok.Data;

@Data
public class UserDto {
    // this is same like a Signup dto but password is not returned
    private Long Id;
    private String email;
    private String name;
}
