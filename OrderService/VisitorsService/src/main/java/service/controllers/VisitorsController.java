package service.controllers;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import service.domain.Visitors;

import java.util.HashMap;

@RestController
@RequestMapping("/visitors")
public class VisitorsController {

    private Visitors visitors;

    @Autowired
    public void setVisitors(Visitors visitors) {
        this.visitors = visitors;
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVisitorById(@PathVariable("id") int id) {
        try {
            return ResponseEntity.
                    status(HttpStatus.OK).
                    body(visitors.getVisitors().stream()
                            .filter((visitor) -> visitor.getId() == id)
                            .findAny()
                            .orElseThrow(NullPointerException::new));
        } catch (NullPointerException e) {
            return new ResponseEntity<Object>(
                    new HashMap<String, String>() {{
                        put("error_message", "Visitor with id  " + id + " does`t exist.");
                        put("error_status", HttpStatus.NOT_FOUND.name());
                    }},
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
