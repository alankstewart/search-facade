package alankstewart.searchfacade.shared.filter;

import alankstewart.searchfacade.shared.filter.Filter.Range;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class RangeValidator implements ConstraintValidator<IsRangeValid, Range> {

    @Override
    public void initialize(IsRangeValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(Range value, ConstraintValidatorContext context) {
        return value.getFrom() <= value.getTo();
    }
}