package com.hardik.OrderService.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //for DB
    private String orderNumber; //shown to client
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItems; //each order contains list of order line items (1:M)
}