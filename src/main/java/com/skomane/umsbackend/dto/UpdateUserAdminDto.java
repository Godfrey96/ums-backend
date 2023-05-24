package com.skomane.umsbackend.dto;

import com.skomane.umsbackend.model.Role;
import lombok.Data;

@Data
public class UpdateUserAdminDto {
    private String myUsername;
    private String phone;
    private String email;
    private Role role;
}
