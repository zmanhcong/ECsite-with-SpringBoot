package edu.poly.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AdminLoginDto {
    private Long userId;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    private Boolean rememberMe = false;
}
