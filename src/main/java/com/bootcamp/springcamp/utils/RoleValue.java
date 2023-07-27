package com.bootcamp.springcamp.utils;

public class RoleValue {
    public static Role getRole(String userType){
        return switch (userType.toLowerCase()) {
            case "user" -> Role.ROLE_USER;
            case "publisher" -> Role.ROLE_PUBLISHER;
            case "admin" -> Role.ROLE_ADMIN;
            default -> null;
        };
    }
}
