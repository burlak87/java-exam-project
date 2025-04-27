package burlakov.lesson.pojo;

import java.math.BigDecimal;

public class MenuItemsBody {
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean isAvailable;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getLocation() {
        return price;
    }

    public Boolean getContactNumber() {
        return isAvailable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(BigDecimal price) {
        this.price = price;
    }

    public void setContactNumber(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
