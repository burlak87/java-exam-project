package burlakov.lesson.service;

import burlakov.lesson.entity.MenuItems;
import burlakov.lesson.repo.MenuItemsJpaRepository;
import burlakov.lesson.repo.MenuItemsRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class MenuItemsService {
    @Autowired
    final MenuItemsJpaRepository menuItemsJpaRepo;

    @Autowired
    final MenuItemsRedisRepository menuItemsRedisRepo;

    @Autowired
    private RedisTemplate<String, MenuItems> redisTemplate;

    private static final String REDIS_KEY = "menuitems";

    public MenuItemsService(MenuItemsJpaRepository menuItemsJpaRepo, MenuItemsRedisRepository menuItemsRedisRepo) {
        this.menuItemsJpaRepo = menuItemsJpaRepo;
        this.menuItemsRedisRepo = menuItemsRedisRepo;
    }

    public MenuItems saveMenuItems(MenuItems menuItems) {
        MenuItems savedMenuItems = menuItemsJpaRepo.save(menuItems);
        ValueOperations<String, MenuItems> operations = redisTemplate.opsForValue();
        operations.set(REDIS_KEY + ":" + savedMenuItems.getId(), savedMenuItems);
        return savedMenuItems;
    }

    public MenuItems getMenuItemsByIdFromDb(Long id) {
        return menuItemsJpaRepo.findById(id).orElse(null);
    }

    public MenuItems getMenuItemsByIdFromRedis(Long id) {
        ValueOperations<String, MenuItems> operations = redisTemplate.opsForValue();
        MenuItems menuItems = operations.get(REDIS_KEY + ":" + id);

        if (menuItems == null) {
            menuItems = menuItemsJpaRepo.findById(id).orElse(null);
            if (menuItems != null) {
                operations.set(REDIS_KEY + ":" + id, menuItems);
            }
        }
        return menuItems;
    }

    public void deleteMenuItems(Long id) {
        menuItemsJpaRepo.deleteById(id);
        redisTemplate.delete(REDIS_KEY + ":" + id);
    }
}
