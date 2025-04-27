package burlakov.lesson.controller;

import burlakov.lesson.entity.MenuItems;
import burlakov.lesson.service.MenuItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class MenuItemsController {
    @Autowired
    private MenuItemsService menuItemsService;

    @PostMapping("add-menuItems")
    public MenuItems createMenuItems(@RequestBody MenuItems menuItems) {
        if (menuItems.getId() == null) {
            menuItems.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        }
        return menuItemsService.saveMenuItems(menuItems);
    }

    @GetMapping("fetch-menuItems/{id}")
    public Optional<MenuItems> getMenuItems(@PathVariable Long id) {
        Optional<MenuItems> menuItems = Optional.ofNullable(menuItemsService.getMenuItemsByIdFromRedis(id));
        if (menuItems.isEmpty()) {
            menuItems = menuItemsService.getMenuItemsByIdFromDb(id);
        }
        return menuItems;
    }
}
