package burlakov.lesson.repo;

import burlakov.lesson.entity.OrderItems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends CrudRepository<OrderItems, Long> {
}
