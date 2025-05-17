package burlakov.lesson.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity(name = "menu_data")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuItems> menuItems;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurants restaurant;

    public List<MenuItems> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItems> menuItems) {
        this.menuItems = menuItems;
    }

    public Restaurants getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurants restaurant) {
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }
}
