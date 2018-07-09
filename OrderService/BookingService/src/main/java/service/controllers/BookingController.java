package service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.delegate.PricingServiceDelegate;
import service.domain.Order;
import service.persistence.BookingService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/booking")
@ComponentScan("service")
public class BookingController {

    private BookingService bookingService;

    private PricingServiceDelegate pricingServiceDelegate;

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    public void setPricingServiceDelegate(PricingServiceDelegate pricingServiceDelegate) {
        this.pricingServiceDelegate = pricingServiceDelegate;
    }

    @RequestMapping(value = "/tickets",
            params = {"film_id", "date_time"},
            method = RequestMethod.GET)
    public ResponseEntity availableTicketsAmount(@RequestParam("film_id") int filmId,
                                                 @RequestParam("date_time")
                                                 @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime dateTime) {

        Integer ticketsCount = bookingService.getAvailableTickets(filmId, dateTime);

        return new ResponseEntity<Object>(Collections.singletonMap(
                "available_tickets_count", ticketsCount == null ? "all" : ticketsCount
        ), HttpStatus.OK
        );
    }


    @RequestMapping(value = "/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity ticketOrder(@RequestBody Order order) {
        //TODO: check that visitor exists.
        try {
            bookingService.addOrder(order);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<Object>(
                    Collections.singletonMap("order_status", "did`t create")
                    ,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<Object>(
                Collections.singletonMap("order_status", "did create")
                ,
                HttpStatus.OK
        );
    }


    @RequestMapping(value = "/user_info/{user_id}", method = RequestMethod.GET)
    ResponseEntity getInformation(@PathVariable("user_id") int userId) {
        try {
            System.out.println("ok");
            return new ResponseEntity<Object>(
                    Optional.ofNullable(bookingService.
                            getUserOrdersInfo(userId).
                            parallelStream().
                            peek(
                                    orderInfo -> {
                                        Double specifiedPrice;
                                        try {
                                            specifiedPrice = pricingServiceDelegate.getPrice(
                                                    userId,
                                                    orderInfo.getTime()
                                            );
                                            orderInfo.setPrice(specifiedPrice);
                                        } catch (IOException | NullPointerException e) {
                                            System.err.println("Could`t parse response from Price service !");
                                        }
                                    }).collect(Collectors.toList())
                    ).orElseThrow(NullPointerException::new)
                    , HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(
                    Collections.singletonMap("order_info_status", "not found"), HttpStatus.BAD_REQUEST
            );
        }
    }

}
