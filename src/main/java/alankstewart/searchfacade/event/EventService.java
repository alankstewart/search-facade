package alankstewart.searchfacade.event;

import alankstewart.searchfacade.shared.AbstractSearchFacadeService;
import alankstewart.searchfacade.shared.filter.Filter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class EventService extends AbstractSearchFacadeService {

    public Optional<Event> findEvent(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Event.class));
    }

    public List<Event> searchEvents(@Valid List<Filter> filter) {
        return mongoTemplate.find(createQuery(filter), Event.class);
    }

    public void clearAndInsertEvents(List<Event> events) {
        mongoTemplate.dropCollection(Event.class);
        mongoTemplate.insertAll(events);
    }
}
