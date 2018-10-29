package alankstewart.searchfacade.user;

import alankstewart.searchfacade.shared.AbstractSearchFacadeService;
import alankstewart.searchfacade.shared.filter.Filter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserService extends AbstractSearchFacadeService {

    public Optional<User> findUser(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, User.class));
    }

    public List<User> searchUsers(@Valid List<Filter> filter) {
        return mongoTemplate.find(createQuery(filter), User.class);
    }

    public void clearAndInsertUsers(List<User> users) {
        mongoTemplate.dropCollection(User.class);
        mongoTemplate.insertAll(users);
    }
}
