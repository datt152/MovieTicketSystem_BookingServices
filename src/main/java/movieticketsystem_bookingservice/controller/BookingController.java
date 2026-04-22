package movieticketsystem_bookingservice.controller;

import movieticketsystem_bookingservice.dto.BookingCreateRequest;
import movieticketsystem_bookingservice.model.Booking;
import movieticketsystem_bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> create(@RequestBody BookingCreateRequest booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }
    @GetMapping
    public ResponseEntity<List<Booking>> getAll() {
        return ResponseEntity.ok(bookingService.getBookings());
    }

}