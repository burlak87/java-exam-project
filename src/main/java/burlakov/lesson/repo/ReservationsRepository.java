package burlakov.lesson.repo;

import burlakov.lesson.entity.Reservations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends CrudRepository<Reservations, Long> {
}
