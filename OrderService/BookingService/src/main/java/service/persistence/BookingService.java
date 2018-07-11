package service.persistence;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.domain.Order;
import service.domain.OrderInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class BookingService {

    private BookingMapper bookingMapper;

    private StampedLock lock = new StampedLock();


    @Autowired
    public void setBookingMapper(BookingMapper bookingMapper) {
        this.bookingMapper = bookingMapper;
    }

    public void addOrder(Order order) throws DataIntegrityViolationException {
        long stamp = lock.writeLock();
        try {
            bookingMapper.addOrder(order);
        } finally {
            lock.unlock(stamp);
        }
    }

    public synchronized  Integer getAvailableTickets(int filmId, LocalDateTime dateTime) {
        return bookingMapper.getAvailableTickets(filmId, dateTime);
    }

    public synchronized List<OrderInfo> getUserOrdersInfo(int userId) throws NullPointerException {
        List<OrderInfo> orderInfoList = bookingMapper.getUserOrdersInfo(userId);
        return orderInfoList.isEmpty() ? null : orderInfoList;
    }

    public void deleteOldSessions() {
        bookingMapper.deleteOldSessions();
    }
}
