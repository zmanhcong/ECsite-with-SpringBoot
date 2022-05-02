package edu.poly.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto  {
    private int customerId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private Date registerdDate;
    private short status;
}
