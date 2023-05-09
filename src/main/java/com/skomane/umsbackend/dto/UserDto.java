package com.skomane.umsbackend.dto;

import lombok.Data;

@Data
public class UserDto {
    private String myUsername;
    private String phone;
    private String email;
    private String role;
    private String status;
}
