package service.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.sun.tools.javac.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.delegate.VisitorServiceDelegate;
import service.domain.TicketPrice;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;

@Controller
@RequestMapping("/price")
public class PricingController {

    private TicketPrice ticketPrice;

    private VisitorServiceDelegate serviceDelegate;

    @Resource
    public void setTicketPrice(TicketPrice ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Autowired
    public void setServiceDelegate(VisitorServiceDelegate serviceDelegate) {
        this.serviceDelegate = serviceDelegate;
    }

    @RequestMapping(params = {"seance_time", "visitor_id"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getTicketPrice(
            @RequestParam("seance_time") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime date,
            @RequestParam("visitor_id") int id) {

        double discount = 0;

        Integer age;

        try {

            age = serviceDelegate.getAge(id);

            if (age > 55)
                discount += 0.2;

            if (date.getHour() >= 10 && date.getHour() < 14)
                discount += 0.3;


        } catch (NullPointerException e) {
            return new ResponseEntity<Object>(
                    new HashMap<String, String>() {{
                        put("error_message", "Could`t parse data from Visitors service!");
                        put("error_status", HttpStatus.NOT_FOUND.name());
                    }},
                    HttpStatus.NOT_FOUND
            );
        }

        return ResponseEntity.
                status(HttpStatus.OK).
                contentType(MediaType.APPLICATION_JSON).
                body(Collections.singletonMap("price", ticketPrice.getPrice() * (1 - discount)));
    }
}
