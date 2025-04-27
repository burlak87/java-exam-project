package burlakov.lesson.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationsBody {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm-ss.SSS")
    private LocalDateTime reservationDate;
    private int numberOfGuests;
    private String specialRequests;
    private BigDecimal totalAmount;
    private Long customerId;
    private Long restaurantId;

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
