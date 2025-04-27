package burlakov.lesson.entity;

import jakarta.persistence.*;
import java.util.List;



@Entity(name = "restaurants_data")
public class Restaurants {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String location;
    private String contactNumber;
    private Float rating;
    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private List<Categories> allCategories;

    // Геттеры и сеттеры
    public List<Categories> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<Categories> allCategories) {
        this.allCategories = allCategories;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public Float getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
