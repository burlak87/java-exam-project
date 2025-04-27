package burlakov.lesson.service;

import burlakov.lesson.entity.MenuItems;
import burlakov.lesson.repo.MenuItemsJpaRepository;
import burlakov.lesson.repo.MenuItemsRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuItemsService {
    @Autowired
    private MenuItemsJpaRepository MenuItemsJpaRepo;

    @Autowired
    private MenuItemsRedisRepository menuItemsRedisRepo;

    @Autowired
    private RedisTemplate<String, MenuItems> redisTemplate;

    private static final String REDIS_KEY = "menuitems";

    public MenuItemsService(MenuItemsJpaRepository MenuItemsJpaRepo, MenuItemsRedisRepository menuItemsRedisRepo) {
        this.MenuItemsJpaRepo = MenuItemsJpaRepo;
        this.menuItemsRedisRepo = menuItemsRedisRepo;
    }

    public MenuItems saveMenuItems(MenuItems menuItems) {
        MenuItems savedMenuItems = MenuItemsJpaRepo.save(menuItems);
        ValueOperations<String, MenuItems> operations = redisTemplate.opsForValue();
        operations.set(REDIS_KEY + ":" + savedMenuItems.getId(), savedMenuItems);
        return savedMenuItems;
    }

    public Optional<MenuItems> getMenuItemsByIdFromDb(Long id) {
        return MenuItemsJpaRepo.findById(id);
    }

    public MenuItems getMenuItemsByIdFromRedis(Long id) {
        ValueOperations<String, MenuItems> operations = redisTemplate.opsForValue();
        MenuItems menuItems = operations.get(REDIS_KEY + ":" + id);
        if (menuItems == null) {
            menuItems = MenuItemsJpaRepo.findById(id).orElse(null);
            if (menuItems != null) {
                operations.set(REDIS_KEY + ":" + id, menuItems);
            }
        }
        return menuItems;
    }

    public void deleteMenuItems(Long id) {
        MenuItemsJpaRepo.deleteById(id);
        redisTemplate.delete(REDIS_KEY + ":" + id);
    }
}
