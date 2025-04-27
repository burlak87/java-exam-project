package burlakov.lesson.repo;

import burlakov.lesson.entity.MenuItems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemsJpaRepository extends CrudRepository<MenuItems, Long> {
}
