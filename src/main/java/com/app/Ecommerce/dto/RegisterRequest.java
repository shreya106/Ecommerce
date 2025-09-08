package com.app.Ecommerce.dto;

import lombok.Data;

@Data
public class RegisterRequest {

	private String email;
    private String password;
    private String name;
    private String address;
    private String role;
    private String country;
    private String location;
}
