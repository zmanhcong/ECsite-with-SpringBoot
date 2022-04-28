package edu.poly.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int orderId;
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @Column(nullable = false)
    private int customerId;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private short status;
}
