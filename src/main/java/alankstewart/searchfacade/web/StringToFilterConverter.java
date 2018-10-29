package alankstewart.searchfacade.web;

import alankstewart.searchfacade.shared.filter.Filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StringToFilterConverter implements Converter<String, Filter> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Filter convert(String s) {
        try {
            return mapper.readValue(s, Filter.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
