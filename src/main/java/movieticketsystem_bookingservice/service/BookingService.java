package movieticketsystem_bookingservice.service;

import movieticketsystem_bookingservice.config.RabbitConfig;
import movieticketsystem_bookingservice.dto.BookingCreateRequest;
import movieticketsystem_bookingservice.dto.BookingDTO;
import movieticketsystem_bookingservice.model.Booking;
import movieticketsystem_bookingservice.repository.BookingRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository repository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Booking  createBooking(BookingCreateRequest request) {
        Booking booking = new Booking();
        booking.setStatus("PENDING");
        booking.setAmount(request.getAmount());
        booking.setUserId(request.getUserId());
        booking.setMovieId(request.getMovieId());
        booking.setSeatNumber(request.getSeatNumber());
        booking = repository.save(booking);

        BookingDTO dto = new BookingDTO(
                booking.getId(),
                booking.getUserId(),
                booking.getMovieId(),
                booking.getSeatNumber(),
                booking.getAmount(),
                booking.getStatus()
        );

//         Publish event lên Exchange
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                "booking.event.created",
                dto
        );

        System.out.println(">>> Đã gửi BOOKING_CREATED cho đơn hàng: " + booking.getId());
        return booking;
    }
    public List<Booking> getBookings() {
        return repository.findAll();
    }

}