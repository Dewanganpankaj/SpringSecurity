package com.example.Security.dto;

import com.example.Security.entities.Enum.Permission;
import com.example.Security.entities.Enum.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    // add some check point logic for verification
    private String email;
    private String password;
    private String name;
    private Set<Role>roles;
    private Set<Permission>permissions;
}
