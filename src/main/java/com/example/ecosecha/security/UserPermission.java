package com.example.ecosecha.security;

public enum UserPermission {
    VER_PRODUCTO("ver:producto"),
    ANADIR_PRODUCTO("anadir:producto"),
    VER_PEDIDO("ver:pedido"),
    ANADIR_PEDIDO("anadir:pedido");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
