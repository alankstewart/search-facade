package alankstewart.searchfacade.web;

import alankstewart.searchfacade.event.Event;
import alankstewart.searchfacade.event.EventService;
import alankstewart.searchfacade.shared.filter.Filter;
import alankstewart.searchfacade.user.User;
import alankstewart.searchfacade.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@Validated
public class SearchFacadeController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping("/events/{id}")
    public ResponseEntity<Event> findEvent(@PathVariable("id") String id) {
        return eventService.findEvent(id).map(ResponseEntity::ok).orElse(notFound().build());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findUser(@PathVariable("id") String id) {
        return userService.findUser(id).map(ResponseEntity::ok).orElse(notFound().build());
    }

    @GetMapping("/events/search")
    public List<Event> searchEvents(@RequestParam(required = false) @Valid List<Filter> filter) {
        return eventService.searchEvents(filter);
    }

    @GetMapping("/users/search")
    public List<User> searchUsers(@RequestParam(required = false) @Valid List<Filter> filter) {
        return userService.searchUsers(filter);
    }
}
