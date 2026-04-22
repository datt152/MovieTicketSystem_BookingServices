package movieticketsystem_bookingservice.dto;

import lombok.Data;

@Data
public class BookingEvent {
    private Long bookingId;
    private Long userId;
    private Double amount;
}