package edu.poly.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail  {
    private  int orderDetailId;
    private  int orderId;
    private  int productId;
    private  int quantity;
    private  int unitPrice;
}
