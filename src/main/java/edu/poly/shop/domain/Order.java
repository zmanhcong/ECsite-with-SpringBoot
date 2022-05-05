package edu.poly.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
//    @Column(nullable = false)
//    private int customerId;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private short status;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails;
}
