package com.skomane.umsbackend.dto;

import lombok.Data;

@Data
public class UpdateUserDto {
    private String myUsername;
    private String phone;
    private String email;
}
