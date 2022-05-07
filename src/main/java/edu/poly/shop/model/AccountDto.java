package edu.poly.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "accounts")
public class AccountDto{
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    private Boolean isEdit = false;    // edit or add
}
