package burlakov.lesson.repo;

import burlakov.lesson.entity.MenuItems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("menuitems")
public interface MenuItemsRedisRepository extends CrudRepository<MenuItems, String> {
}