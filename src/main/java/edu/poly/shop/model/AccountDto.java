package edu.poly.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AccountDto{
    private Long userId;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    private Boolean isEdit = false;    // edit or add
}
