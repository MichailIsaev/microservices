package checker.persistence;

import checker.domain.Seance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SeanceMapper {

    @Select("SELECT * FROM seance")
    List<Seance> getSeances();

    @Select("SELECT ticket_count FROM seance WHERE id = #{id}")
    long getAvailableSeats(@Param("id") Integer id);

}
