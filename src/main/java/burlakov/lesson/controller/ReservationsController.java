package burlakov.lesson.controller;

import burlakov.lesson.entity.Customers;
import burlakov.lesson.entity.Reservations;
import burlakov.lesson.entity.OrderItems;
import burlakov.lesson.entity.Restaurants;
import burlakov.lesson.pojo.ReservationsBody;
import burlakov.lesson.repo.OrderItemsRepository;
import burlakov.lesson.repo.ReservationsRepository;
import burlakov.lesson.repo.CustomersRepository;
import burlakov.lesson.repo.RestaurantsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ReservationsController {
    private final ReservationsRepository reservationRepo;
    private final OrderItemsRepository orderItemsRepo;
    private final CustomersRepository customersRepo;
    private final RestaurantsRepository restaurantsRepo;

    public ReservationsController(ReservationsRepository reservationRepo, OrderItemsRepository orderItemsRepo, CustomersRepository customersRepo, RestaurantsRepository restaurantsRepo) {
        this.reservationRepo = reservationRepo;
        this.orderItemsRepo = orderItemsRepo;
        this.customersRepo = customersRepo;
        this.restaurantsRepo = restaurantsRepo;
    }

    @GetMapping("fetch-reservations")
    public ResponseEntity<Iterable<Reservations>> getReservations() {
        return ResponseEntity.ok(reservationRepo.findAll());
    }

    @PostMapping("add-reservations")
    public ResponseEntity<Reservations> add(@RequestBody ReservationsBody reservationsBody) {
        Reservations reservations = new Reservations();
        reservations.setReservationDate(reservationsBody.getReservationDate());
        reservations.setNumberOfGuests(reservationsBody.getNumberOfGuests());
        reservations.setSpecialRequests(reservationsBody.getSpecialRequests());
        reservations.setTotalAmount(reservationsBody.getTotalAmount());

        if (reservationsBody.getCustomerId() != null) {
            Optional<Customers> customerOptional = customersRepo.findById(reservationsBody.getCustomerId());
            if (customerOptional.isPresent()) {
                reservations.setCustomer(customerOptional.get());
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } else {
            return ResponseEntity.badRequest().body(null);
        }

        if (reservationsBody.getRestaurantId() != null) {
            Optional<Restaurants> restaurantOptional = restaurantsRepo.findById(reservationsBody.getRestaurantId());
            if (restaurantOptional.isPresent()) {
                reservations.setRestaurants(restaurantOptional.get());
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } else {
            return ResponseEntity.badRequest().body(null);
        }

        Reservations savedReservation = reservationRepo.save(reservations);
        return ResponseEntity.ok(savedReservation);
    }

    @PostMapping("add-reservations/{id}/orders/{orderItemsId}")
    public ResponseEntity<String> addOrderToReservation(@PathVariable("id") Long id, @PathVariable("orderItemsId") Long orderItemsId) {
        Optional<Reservations> reservationsOptional = reservationRepo.findById(id);
        if (reservationsOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("No reservation found with id: " + id);
        }

        Reservations reservations = reservationsOptional.get();
        Optional<OrderItems> orderItemsOptional = orderItemsRepo.findById(orderItemsId);
        if (orderItemsOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("No order item found with id: " + orderItemsId);
        }

        OrderItems orderItem = orderItemsOptional.get();
        orderItem.setReservations(reservations); // Устанавливаем связь с бронированием
        reservations.getAllOrderItems().add(orderItem); // Добавляем заказ в бронирование
        reservationRepo.save(reservations); // Сохраняем изменения в бронировании

        return ResponseEntity.ok("Order item added to reservation.");
    }

    @PutMapping("update-restaurants/{id}")
    public ResponseEntity<Reservations> updateReservations(@PathVariable Long id, @RequestBody ReservationsBody reservationsBody) {
        Optional<Reservations> reservationsOptional = reservationRepo.findById(id);
        if (reservationsOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Reservations reservations = reservationsOptional.get();
        reservations.setReservationDate(reservationsBody.getReservationDate());
        reservations.setNumberOfGuests(reservationsBody.getNumberOfGuests());
        reservations.setSpecialRequests(reservationsBody.getSpecialRequests());
        reservations.setTotalAmount(reservationsBody.getTotalAmount());
        Reservations updatedReservations = reservationRepo.save(reservations);

        return ResponseEntity.ok(updatedReservations);
    }

    @DeleteMapping("delete-restaurants/{id}")
    public ResponseEntity<Void> DeleteReservations(@PathVariable Long id) {
        Optional<Reservations> reservationsOptional = reservationRepo.findById(id);

        if (reservationsOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        reservationRepo.delete(reservationsOptional.get());
        return ResponseEntity.noContent().build();

    }
}
