package alankstewart.searchfacade.shared.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@IsFilterValid
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Filter {

    public enum Operator {eq, gte, lte}

    @NotBlank
    @JsonProperty(required = true)
    private String attribute;

    @NotNull
    @JsonProperty(required = true)
    private Operator operator;

    private Object value;

    @Valid
    private Range range;

    public String getAttribute() {
        return attribute;
    }

    public Filter setAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public Operator getOperator() {
        return operator;
    }

    public Filter setOperator(Operator operator) {
        this.operator = operator;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public Filter setValue(Object value) {
        this.value = value;
        return this;
    }

    public Range getRange() {
        return range;
    }

    public Filter setRange(Range range) {
        this.range = range;
        return this;
    }

    @IsRangeValid
    public static class Range {

        @NotNull
        @JsonProperty(required = true)
        private long from;

        @NotNull
        @JsonProperty(required = true)
        private long to;

        public long getFrom() {
            return from;
        }

        public Range setFrom(long from) {
            this.from = from;
            return this;
        }

        public long getTo() {
            return to;
        }

        public Range setTo(long to) {
            this.to = to;
            return this;
        }
    }
}
