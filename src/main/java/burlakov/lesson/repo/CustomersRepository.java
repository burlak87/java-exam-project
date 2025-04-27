package burlakov.lesson.repo;

import burlakov.lesson.entity.Customers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends CrudRepository<Customers, Long> {
}
