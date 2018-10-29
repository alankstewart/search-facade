package alankstewart.searchfacade.shared;

import alankstewart.searchfacade.shared.filter.Filter;
import alankstewart.searchfacade.shared.filter.Filter.Range;
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
        Object value = f.getValue();
        String attribute = f.getAttribute();
        switch (f.getOperator()) {
            case eq:
                Range range = f.getRange();
                if (value == null && range != null) {
                    query.addCriteria(where(attribute).gte(range.getFrom()).lte(range.getTo()));
                } else if (value != null && range == null) {
                    query.addCriteria(where(attribute).is(value));
                }
                break;
            case gte:
                query.addCriteria(where(attribute).gte(value));
                break;
            case lte:
                query.addCriteria(where(attribute).lte(value));
                break;
        }
    }
}
