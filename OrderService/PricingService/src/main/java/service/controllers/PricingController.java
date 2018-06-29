package service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.VisitorServiceDelegate;
import service.domain.TicketPrice;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

@Controller
@RequestMapping("/price")
public class PricingController {

    private TicketPrice ticketPrice;

    private VisitorServiceDelegate serviceDelegate;


    @Autowired
    public void setTicketPrice(TicketPrice ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Autowired
    public void setServiceDelegate(VisitorServiceDelegate serviceDelegate) {
        this.serviceDelegate = serviceDelegate;
    }

    @RequestMapping(params = {"seance_time", "visitor_id"}, method = RequestMethod.GET)
    @ResponseBody
    public Double getTicketPrice(@RequestParam("seance_time") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss") LocalDateTime date,
                                 @RequestParam("visitor_id") Integer id) {//TODO: age or id{

        double discount = 0;

        int age = 0;
        try {
            age = serviceDelegate.getAge(id);
        } catch (IOException e) {
            //TODO: ExceptionHandler
            e.printStackTrace();
        }

        if (age > 55)
            discount += 0.2;

        if (date.getHour() >= 10 && date.getHour() < 14)
            discount += 0.3;

        return ticketPrice.getPrice() * (1 - discount);
    }


}
