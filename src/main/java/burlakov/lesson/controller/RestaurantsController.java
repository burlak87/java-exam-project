package burlakov.lesson.controller;

import burlakov.lesson.entity.Menu;
import burlakov.lesson.entity.Restaurants;
import burlakov.lesson.pojo.RestaurantsBody;
import burlakov.lesson.service.RestaurantsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantsController {
    private final RestaurantsService restaurantsService;

    public RestaurantsController(RestaurantsService restaurantsService) {
        this.restaurantsService = restaurantsService;
    }

    @GetMapping("restaurants/index")
    public ResponseEntity<?> getRestaurants() {
        return ResponseEntity.ok(restaurantsService.getAllRestaurants());
    }

    @PostMapping("restaurants/store")
    public ResponseEntity<?> addRestaurant(@RequestBody RestaurantsBody restaurantsBody) {
        Restaurants restaurants = restaurantsService.createRestaurant(restaurantsBody);
        return ResponseEntity.ok(restaurants);
    }

    @PostMapping("restaurants/store/{restaurantId}/menu")
    public ResponseEntity<?> addMenuToRestaurant(@PathVariable Long restaurantId, @RequestBody Menu menu) {
        boolean success = restaurantsService.addMenuToRestaurant(restaurantId, menu);
        if (!success) {
            return ResponseEntity.badRequest().body("Restaurant not found with id: " + restaurantId);
        }
        return ResponseEntity.ok("Menu added to restaurant.");
    }
}