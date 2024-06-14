package com.example.ecosecha.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.ecosecha.security.UserPermission.*;

public enum UserRole {
    ADMIN(Sets.newHashSet(VER_PRODUCTO, ANADIR_PRODUCTO, VER_PEDIDO, ANADIR_PEDIDO)),
    SELLER(Sets.newHashSet(VER_PRODUCTO, ANADIR_PRODUCTO, VER_PEDIDO)),
    CUSTOMER(Sets.newHashSet(VER_PRODUCTO, VER_PEDIDO, ANADIR_PEDIDO));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
