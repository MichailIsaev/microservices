package service.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import service.domain.Visitor;
import service.domain.Visitors;

@RestController
@RequestMapping("/visitor")
public class VisitorsController {

    //TODO: ExceptionHandler

    private Visitors visitors;

    @Autowired
    public void setVisitors(Visitors visitors) {
        this.visitors = visitors;
    }

    @ResponseBody
    @RequestMapping("/{id}")
    public Visitor getVisitorById(@PathVariable("id") Integer id) {
        return visitors.getVisitors().stream()
                .filter((visitor) -> visitor.getId().equals(id))
                .findAny()
                .orElseThrow(NullPointerException::new);
    }
}
