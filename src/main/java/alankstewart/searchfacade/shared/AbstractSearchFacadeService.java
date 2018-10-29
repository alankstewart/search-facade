package alankstewart.searchfacade.shared;

import alankstewart.searchfacade.shared.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public abstract class AbstractSearchFacadeService {

    @Autowired
    protected MongoTemplate mongoTemplate;

    protected Query createQuery(List<Filter> filter) {
        Query query = new Query();
        if (filter != null) {
            filter.forEach(f -> addCriteria(query, f));
        }
        return query;
    }

    private void addCriteria(Query query, Filter f) {
        switch (f.getOperator()) {
            case eq:
                if (f.getValue() == null && f.getRange() != null) {
                    query.addCriteria(where(f.getAttribute()).gte(f.getRange().getFrom()).lte(f.getRange().getTo()));
                } else if (f.getValue() != null && f.getRange() == null) {
                    query.addCriteria(where(f.getAttribute()).is(f.getValue()));
                }
                break;
            case gte:
                query.addCriteria(where(f.getAttribute()).gte(f.getValue()));
                break;
            case lte:
                query.addCriteria(where(f.getAttribute()).lte(f.getValue()));
                break;
        }
    }
}
