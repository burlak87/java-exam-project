package burlakov.lesson.controller;

import burlakov.lesson.entity.Menu;
import burlakov.lesson.entity.MenuItems;
import burlakov.lesson.entity.Restaurants;
import burlakov.lesson.entity.Category;
import burlakov.lesson.pojo.MenuBody;
import burlakov.lesson.repo.MenuRepository;
import burlakov.lesson.repo.RestaurantsRepository;
import burlakov.lesson.repo.CategoryRepository;
import burlakov.lesson.service.MenuItemsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@RestController
public class MenuController {
    private final MenuRepository menuRepository;
    private final RestaurantsRepository restaurantsRepository;
    private final CategoryRepository repository;
    private final MenuItemsService menuItemsService;

    public MenuController(MenuRepository menuRepository, RestaurantsRepository restaurantsRepository, CategoryRepository repository, MenuItemsService menuItemsService) {
        this.menuRepository = menuRepository;
        this.restaurantsRepository = restaurantsRepository;
        this.repository = repository;
        this.menuItemsService = menuItemsService;
    }

    @GetMapping("menus/index")
    public ResponseEntity<Iterable<Menu>> getMenus() {
        return ResponseEntity.ok(menuRepository.findAll());
    }

    @GetMapping("menus/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Optional<Menu> menuOpt = menuRepository.findById(id);
        return menuOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("menus/store")
    public ResponseEntity<Menu> add(@RequestBody MenuBody menuBody) {
        Menu menu = new Menu();
        menu.setName(menuBody.getName());
        menu.setDescription(menuBody.getDescription());
        menu.setIsActive(menuBody.getIsActive());
        Optional<Restaurants> restaurantOpt = restaurantsRepository.findById(menuBody.getRestaurantId());
        if (restaurantOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        menu.setRestaurant(restaurantOpt.get());
        menuRepository.save(menu);
        return ResponseEntity.ok(menu);
    }

    @GetMapping("menus/categories/index")
    public ResponseEntity<Iterable<Category>> getCategories() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("menus/categories/store")
    public ResponseEntity<Category> add(@RequestBody Category category) {
        repository.save(category);
        return ResponseEntity.ok(category);
    }

    @GetMapping("menus/items/show/{id}")
    public ResponseEntity<MenuItems> getMenuItems(@PathVariable Long id) {
        MenuItems menuItems = menuItemsService.getMenuItemsByIdFromRedis(id);
        if (menuItems == null) {
            menuItems = menuItemsService.getMenuItemsByIdFromDb(id);
        }
        if (menuItems != null) {
            return ResponseEntity.ok(menuItems);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("menus/items/create")
    public MenuItems createMenuItems(@RequestBody MenuItems menuItems) {
        if (menuItems.getId() == null) {
            menuItems.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        }
        return menuItemsService.saveMenuItems(menuItems);
    }
}

