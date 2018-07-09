package service.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.annotations.*;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import service.domain.Order;
import service.domain.OrderInfo;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Mapper
public interface BookingMapper {

    String AVAILABLE_TICKETS = "SELECT hall.capacity - sum(booking.tickets_amount) AS available FROM hall , booking  " +
            "WHERE ( hall.id ,  booking.session_id) IN " +
            "(SELECT session.hall_id , session.id  FROM session , film  " +
            "WHERE session.film_id = #{film_id} AND session.time = #{time})  " +
            "GROUP BY hall.id , booking.session_id";

    String USER_INFO = "SELECT film.title as film_title ,\n" +
            "         film.description as film_description ,\n" +
            "         hall.id as hall_id ,\n" +
            "         session.time as session_time ,\n" +
            "         tickets_amount \n" +
            "         FROM (SELECT session.id as session_id ,\n" +
            "                                 film_id ,\n" +
            "                                 hall_id ,\n" +
            "                                 tickets_amount\n" +
            "                                 FROM session INNER JOIN booking ON (booking.session_id = session.id AND booking.user_id = #{user_id})\n" +
            "                              ) session_info\n" +
            "    INNER JOIN film ON session_info.film_id = film.id\n" +
            "    INNER JOIN hall ON session_info.hall_id = hall.id\n" +
            "    INNER JOIN session ON session_info.session_id = session.id";

    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "sessionId", column = "session_id"),
            @Result(property = "ticketsAmount", column = "tickets_amount")
    })
    @Insert("INSERT INTO booking(user_id , session_id , tickets_amount ) VALUES(#{userId}, #{sessionId} , #{ticketsAmount})")
    void addOrder(Order order) throws DataIntegrityViolationException;

    @Select(AVAILABLE_TICKETS)
    Integer getAvailableTickets(@Param("film_id") int filmId, @Param("time") LocalDateTime dateTime);

    @Results({
            @Result(property = "filmTitle", column = "film_title"),
            @Result(property = "filmDescription", column = "film_description"),
            @Result(property = "hallId", column = "hall_id"),
            @Result(property = "time", column = "session_time"),
            @Result(property = "ticketsAmount", column = "tickets_amount")
    })
    @Select(USER_INFO)
    List<OrderInfo> getUserOrdersInfo(@Param("user_id") int userId) throws NullPointerException;

    @Delete("DELETE FROM booking WHERE id IN(" +
            "SELECT id FROM booking WHERE session_id IN (" +
            "SELECT id FROM session WHERE time < now()" +
            ")" +
            ");" +
            "DELETE FROM session WHERE time < now()")
    void deleteOldSessions();
}

