package com.example.Security.utils;

import com.example.Security.entities.Enum.Permission;
import com.example.Security.entities.Enum.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.Security.entities.Enum.Permission.*;
import static com.example.Security.entities.Enum.Role.*;
import static com.example.Security.entities.Enum.Role.ADMIN;
import static com.example.Security.entities.Enum.Role.*;

public class PermissionMapping {

    private static final Map<Role, Set<Permission>> map = Map.of(
            USER, Set.of(USER_VIEW, POST_VIEW),
            CREATER, Set.of(USER_VIEW, POST_VIEW, USER_UPDATE, POST_UPDATE),
            ADMIN, Set.of(USER_DELETE, USER_CREATED, POST_DELETE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role) {
        Set<Permission> permissions = map.getOrDefault(role, Set.of());
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}