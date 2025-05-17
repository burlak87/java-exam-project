package burlakov.lesson.pojo;

import java.util.List;

public class MenuBody {
    private String name;
    private String description;
    private Boolean isActive;
    private Long restaurantId;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
