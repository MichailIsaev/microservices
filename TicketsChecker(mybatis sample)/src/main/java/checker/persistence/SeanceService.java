package checker.persistence;

import checker.domain.Seance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SeanceService {

    @Autowired
    public void setMapper(SeanceMapper mapper) {
        this.mapper = mapper;
    }

    private SeanceMapper mapper;

    public long getAvailableSeatsCount(Integer id) {
        return mapper.getAvailableSeats(id);
    }

    public void addSeance(Seance seance) {
        //TODO: rewrite with SeanceDAO
        System.out.println(Objects.toString(seance));
        mapper.addSeance(seance);
    }

}
