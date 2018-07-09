package service.cleaner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import service.persistence.BookingService;

@Component
public class SessionCleaner {

    private BookingService bookingService;

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void deleteOldSessions() {
        bookingService.deleteOldSessions();
    }
}
