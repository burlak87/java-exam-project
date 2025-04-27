package burlakov.lesson.entity;

import org.springframework.data.redis.core.RedisHash;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity(name = "menuItems_data")
@RedisHash("menuitems")
public class MenuItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories categories;

    public Categories getCategory() {
        return categories;
    }

    public void setCategory(Categories category) {
        this.categories = category;
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

    public BigDecimal getPrice() {
        return price;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
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

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
