package burlakov.lesson.controller;

import burlakov.lesson.entity.Restaurants;
import burlakov.lesson.entity.Categories;
import burlakov.lesson.pojo.RestaurantsBody;
import burlakov.lesson.repo.RestaurantsRepository;
import burlakov.lesson.repo.CategoriesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RestaurantsController {
    private final RestaurantsRepository restaurantsRepo;
    private final CategoriesRepository categoriesRepo;

    public RestaurantsController(RestaurantsRepository restaurantRepo, CategoriesRepository categoriesRepo) {
        this.restaurantsRepo = restaurantRepo;
        this.categoriesRepo = categoriesRepo;
    }

    @GetMapping("fetch-restaurants")
    public ResponseEntity<Iterable<Restaurants>> getRestaurants() {
        return ResponseEntity.ok(restaurantsRepo.findAll());
    }

    @PostMapping("add-restaurants")
    public ResponseEntity<Restaurants> add(@RequestBody RestaurantsBody restaurantsBody) {
        Restaurants restaurant = new Restaurants();
        restaurant.setName(restaurantsBody.getName());
        restaurant.setLocation(restaurantsBody.getLocation());
        restaurant.setContactNumber(restaurantsBody.getContactNumber());
        restaurant.setRating(restaurantsBody.getRating());
        restaurant.setDescription(restaurantsBody.getDescription());

        List<Categories> categoriesList = new ArrayList<>();
        for (String categoryName : restaurantsBody.getCategories()) {
            Categories existingCategory = categoriesRepo.findByName(categoryName);
            if (existingCategory == null) {
                existingCategory = new Categories();
                existingCategory.setName(categoryName);
                categoriesRepo.save(existingCategory);
            }
            categoriesList.add(existingCategory);
        }

        restaurant.setAllCategories(categoriesList);
        restaurantsRepo.save(restaurant);

        return ResponseEntity.ok(restaurant);
    }

    @PostMapping("add-restaurants/{id}/{categoryName}")
    public ResponseEntity<String> add(@PathVariable("id") Long id, @PathVariable("categoryName") String categoryName) {
        Optional<Restaurants> restaurantsOptional = restaurantsRepo.findById(id);
        if (restaurantsOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("No restaurant found with id: " + id);
        }

        Restaurants restaurant = restaurantsOptional.get();

        Categories existingCategory = categoriesRepo.findByName(categoryName);

        if (existingCategory == null) {
            existingCategory = new Categories();
            existingCategory.setName(categoryName);
            categoriesRepo.save(existingCategory);
        }

        if (restaurant.getAllCategories() == null) {
            restaurant.setAllCategories(new ArrayList<>());
        }

        if (!restaurant.getAllCategories().contains(existingCategory)) {
            restaurant.getAllCategories().add(existingCategory);
            restaurantsRepo.save(restaurant);
            return ResponseEntity.ok("Category added to restaurant.");
        } else {
            return ResponseEntity.ok("Category already exists in the restaurant.");
        }
    }
}
