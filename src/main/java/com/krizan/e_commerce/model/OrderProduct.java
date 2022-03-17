package com.krizan.e_commerce.model;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "order_products")
@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderProductId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private Product product;

    @NonNull
    private Integer quantity;

    public BigDecimal getTotalPrice() {
        if (getProduct().getFinalUnitPrice() != null) {
            return getProduct().getFinalUnitPrice().multiply(BigDecimal.valueOf(getQuantity()));
        } else throw new NullPointerException();
    }
}