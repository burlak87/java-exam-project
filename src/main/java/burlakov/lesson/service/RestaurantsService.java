package burlakov.lesson.service;

import burlakov.lesson.entity.Customers;
import burlakov.lesson.entity.Menu;
import burlakov.lesson.entity.Restaurants;
import burlakov.lesson.pojo.RestaurantsBody;
import burlakov.lesson.repo.RestaurantsRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantsService {
    private final RestaurantsRepository restaurantsRepo;

    public RestaurantsService(RestaurantsRepository restaurantsRepo) {
        this.restaurantsRepo = restaurantsRepo;
    }

    public Iterable<Restaurants> getAllRestaurants() {
        return restaurantsRepo.findAll();
    }

    public Restaurants createRestaurant(RestaurantsBody restaurantsBody) {
        Restaurants restaurant = new Restaurants();
        restaurant.setName(restaurantsBody.getName());
        restaurant.setLocation(restaurantsBody.getLocation());
        restaurant.setContactNumber(restaurantsBody.getContactNumber());
        restaurant.setRating(restaurantsBody.getRating());
        restaurant.setDescription(restaurantsBody.getDescription());
        return restaurantsRepo.save(restaurant);
    }

    public Optional<Restaurants> findById(Long id) {
        return restaurantsRepo.findById(id);
    }

    public boolean addMenuToRestaurant(Long restaurantId, Menu menu) {
        Optional<Restaurants> restaurantOpt = restaurantsRepo.findById(restaurantId);
        if (restaurantOpt.isEmpty()) {
            return false;
        }
        Restaurants restaurant = restaurantOpt.get();
        if (restaurant.getMenus() == null) {
            restaurant.setMenus(new java.util.ArrayList<>());
        }
        restaurant.getMenus().add(menu);
        restaurantsRepo.save(restaurant);
        return true;
    }
}
