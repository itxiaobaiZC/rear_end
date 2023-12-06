package com.itzc.rear_end.controller.dto;

import lombok.Data;

@Data
public class UserPasswordDTO {
    private String name;
    private String password;
    private String newPassword;
}
