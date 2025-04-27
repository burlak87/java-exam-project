package burlakov.lesson.repo;

import burlakov.lesson.entity.Restaurants;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantsRepository extends CrudRepository<Restaurants, Long> {
}
