package burlakov.lesson.service;

import burlakov.lesson.entity.Customers;
import burlakov.lesson.entity.Menu;
import burlakov.lesson.entity.OrderItems;
import burlakov.lesson.entity.Reservations;
import burlakov.lesson.entity.Restaurants;
import burlakov.lesson.repo.CustomersRepository;
import burlakov.lesson.repo.OrderItemsRepository;
import burlakov.lesson.repo.ReservationsRepository;
import burlakov.lesson.repo.RestaurantsRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationsService {
    private final ReservationsRepository reservationsRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final CustomersRepository customersRepository;
    private final RestaurantsRepository restaurantsRepository;

    public ReservationsService(ReservationsRepository reservationsRepository, OrderItemsRepository orderItemsRepository, CustomersRepository customersRepository, RestaurantsRepository restaurantsRepository) {
        this.reservationsRepository = reservationsRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.customersRepository = customersRepository;
        this.restaurantsRepository = restaurantsRepository;
    }

    public Iterable<Reservations> getAllReservations() {
        return reservationsRepository.findAll();
    }

    public Optional<Customers> findCustomerById(Long id) {
        return customersRepository.findById(id);
    }

    public Optional<Restaurants> findRestaurantById(Long id) {
        return restaurantsRepository.findById(id);
    }

    public Reservations createReservation(Reservations reservation, Long customerId, Long restaurantId) {
        Optional<Customers> customerOpt = findCustomerById(customerId);
        Optional<Restaurants> restaurantOpt = findRestaurantById(restaurantId);

        if (customerOpt.isEmpty() || restaurantOpt.isEmpty()) {
            throw new IllegalArgumentException("Customer or Restaurant not found");
        }

        reservation.setCustomer(customerOpt.get());
        reservation.setRestaurants(restaurantOpt.get());
        return reservationsRepository.save(reservation);
    }

    public boolean addOrderToReservation(Long reservationId, Long orderItemsId) {
        Optional<Reservations> reservationOpt = reservationsRepository.findById(reservationId);
        Optional<OrderItems> orderItemOpt = orderItemsRepository.findById(orderItemsId);

        if (reservationOpt.isEmpty() || orderItemOpt.isEmpty()) {
            return false;
        }

        Reservations reservation = reservationOpt.get();
        OrderItems orderItem = orderItemOpt.get();
        orderItem.setReservations(reservation);
        reservation.getAllOrderItems().add(orderItem);
        reservationsRepository.save(reservation);
        return true;
    }

    public Optional<Reservations> updateReservation(Long id, Reservations updatedReservation, Long customerId, Long restaurantId) {
        Optional<Reservations> reservationOpt = reservationsRepository.findById(id);

        if (reservationOpt.isEmpty()) {
            return Optional.empty();
        }

        Reservations reservation = reservationOpt.get();
        reservation.setReservationDate(updatedReservation.getReservationDate());
        reservation.setNumberOfGuests(updatedReservation.getNumberOfGuests());
        reservation.setSpecialRequests(updatedReservation.getSpecialRequests());
        reservation.setTotalAmount(updatedReservation.getTotalAmount());

        if (customerId != null) {
            findCustomerById(customerId).ifPresent(reservation::setCustomer);
        }

        if (restaurantId != null) {
            findRestaurantById(restaurantId).ifPresent(reservation::setRestaurants);
        }

        Reservations saved = reservationsRepository.save(reservation);
        return Optional.of(saved);
    }

    public boolean deleteReservation(Long id) {
        Optional<Reservations> reservationOpt = reservationsRepository.findById(id);

        if (reservationOpt.isEmpty()) {
            return false;
        }

        reservationsRepository.delete(reservationOpt.get());
        return true;
    }
}
