package checker.controllers;

import checker.persistence.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
