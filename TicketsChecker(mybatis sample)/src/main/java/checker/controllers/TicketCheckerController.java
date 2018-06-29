package checker.controllers;

import checker.domain.Seance;
import checker.persistence.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@ComponentScan("checker")
@RequestMapping("/checker")
public class TicketCheckerController {

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Autowired
    public void setSeanceService(SeanceService seanceService) {
        this.seanceService = seanceService;
    }

    private DiscoveryClient discoveryClient;

    private SeanceService seanceService;

    @RequestMapping(value = "/{id}/seats", method = RequestMethod.GET)
    public @ResponseBody
    Long availableSeatsCount(@PathVariable("id") Integer id) {
        return seanceService.getAvailableSeatsCount(id);
    }


    //TODO: migrate to another_controller and fix 415
    @RequestMapping(value = "/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void addSeance(@RequestBody Seance seance) {
        seanceService.addSeance(seance);
    }
}
