package burlakov.lesson.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity(name = "orderItems_data")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private BigDecimal priceAtOrder;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItems menuItems;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservations reservations; // Связь с Reservations

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtOrder() {
        return priceAtOrder;
    }

    public void setPriceAtOrder(BigDecimal priceAtOrder) {
        this.priceAtOrder = priceAtOrder;
    }

    public MenuItems getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(MenuItems menuItems) {
        this.menuItems = menuItems;
    }

    public Reservations getReservations() {
        return reservations;
    }

    public void setReservations(Reservations reservations) {
        this.reservations = reservations;
    }
}
