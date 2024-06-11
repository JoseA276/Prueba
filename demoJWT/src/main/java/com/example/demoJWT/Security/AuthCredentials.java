package com.example.demoJWT.Security;

import lombok.Data;

@Data
public class AuthCredentials {
    private String email;
    private String password;
}
