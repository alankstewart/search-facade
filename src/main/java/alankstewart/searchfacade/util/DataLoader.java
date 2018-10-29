package alankstewart.searchfacade.util;

import alankstewart.searchfacade.event.Event;
import alankstewart.searchfacade.event.EventService;
import alankstewart.searchfacade.user.User;
import alankstewart.searchfacade.user.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... strings) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getResourceAsStream("/sample-data/events.json")) {
            eventService.clearAndInsertEvents(mapper.readValue(is, new TypeReference<List<Event>>() {}));
        }
        try (InputStream is = getClass().getResourceAsStream("/sample-data/users.json")) {
            userService.clearAndInsertUsers(mapper.readValue(is, new TypeReference<List<User>>() {}));
        }
    }
}