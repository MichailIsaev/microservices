package checker.persistence;

import checker.domain.Seance;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SeanceMapper {

    @Results({
            @Result(property = "movie", column = "movie"),
            @Result(property = "seanceTime", column = "movie_time"),
            @Result(property = "ticketCount", column = "ticket_count")
    })


    @Select("SELECT * FROM seance")
    List<Seance> getSeances();

    @Select("SELECT ticket_count FROM seance WHERE id = #{id}")
    long getAvailableSeats(@Param("id") Integer id);

    @Insert("INSERT INTO seance(movie_time , movie , ticket_count) VALUES(#{seanceTime}, #{movie} , #{ticketCount})")
    void addSeance(Seance seance);
}
