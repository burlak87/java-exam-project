package burlakov.lesson.repo;

import burlakov.lesson.entity.Categories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends CrudRepository<Categories, Long> {
    Categories findByName(String name);
}
