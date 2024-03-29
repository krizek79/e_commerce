package com.krizan.e_commerce.model;

import com.krizan.e_commerce.utils.OrderStatus;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;
    @CreationTimestamp
    @CreatedDate
    private LocalDateTime dateCreated;
    private Integer numberOfProducts;
    private BigDecimal totalOrderPrice;
    private OrderStatus status;
    private Boolean payed;

    public Order(ShoppingCart shoppingCart, Customer customer) {
        this.customer = customer;
        this.shoppingCart = shoppingCart;
        this.numberOfProducts = calcNumberOfProducts();
        this.totalOrderPrice = calcTotalOrderPrice();
        this.status = OrderStatus.WAITING;
        this.payed = false;
    }

    private BigDecimal calcTotalOrderPrice() {
        var total = BigDecimal.ZERO;
        for (var entry: shoppingCart.getProducts()) {
            total = total.add(
                BigDecimal.valueOf(entry.getAmount())
                .multiply(entry.getProduct().getFinalUnitPrice())
            );
        }
        return total;
    }

    private Integer calcNumberOfProducts() {
        Integer total = 0;
        for (var product: shoppingCart.getProducts()) {
            total += product.getAmount();
        }
        return total;
    }
}
