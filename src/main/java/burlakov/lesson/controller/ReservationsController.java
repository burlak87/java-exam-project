package burlakov.lesson.controller;

import burlakov.lesson.entity.Menu;
import burlakov.lesson.entity.OrderItems;
import burlakov.lesson.entity.Reservations;
import burlakov.lesson.pojo.ReservationsBody;
import burlakov.lesson.service.ReservationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationsController {
    private final ReservationsService reservationsService;

    public ReservationsController(ReservationsService reservationsService) {
        this.reservationsService = reservationsService;
    }

    @GetMapping("reservations/index")
    public ResponseEntity<?> getReservations() {
        return ResponseEntity.ok(reservationsService.getAllReservations());
    }

    @PostMapping("reservations/store")
    public ResponseEntity<?> add(@RequestBody ReservationsBody reservationsBody) {
        try {
            Reservations reservation = new Reservations();
            reservation.setReservationDate(reservationsBody.getReservationDate());
            reservation.setNumberOfGuests(reservationsBody.getNumberOfGuests());
            reservation.setSpecialRequests(reservationsBody.getSpecialRequests());
            reservation.setTotalAmount(reservationsBody.getTotalAmount());
            Reservations saved = reservationsService.createReservation(reservation, reservationsBody.getCustomerId(), reservationsBody.getRestaurantId());
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("reservations/store/{id}/orders/{orderItemsId}")
    public ResponseEntity<?> addOrderToReservation(@PathVariable Long id, @PathVariable Long orderItemsId) {
        boolean success = reservationsService.addOrderToReservation(id, orderItemsId);
        if (!success) {
            return ResponseEntity.badRequest().body("Reservation or OrderItem not found");
        }
        return ResponseEntity.ok("Order item added to reservation.");
    }

    @PutMapping("reservations/update/{id}")
    public ResponseEntity<?> updateReservations(@PathVariable Long id, @RequestBody ReservationsBody reservationsBody) {
        Reservations updatedReservations = new Reservations();
        updatedReservations.setReservationDate(reservationsBody.getReservationDate());
        updatedReservations.setNumberOfGuests(reservationsBody.getNumberOfGuests());
        updatedReservations.setSpecialRequests(reservationsBody.getSpecialRequests());
        updatedReservations.setTotalAmount(reservationsBody.getTotalAmount());
        return reservationsService.updateReservation(id, updatedReservations, reservationsBody.getCustomerId(), reservationsBody.getRestaurantId()).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("reservations/destroy/{id}")
    public ResponseEntity<?> deleteReservations(@PathVariable Long id) {
        boolean success = reservationsService.deleteReservation(id);
        if (!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
