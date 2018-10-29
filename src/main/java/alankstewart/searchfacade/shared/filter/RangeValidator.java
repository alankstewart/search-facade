package alankstewart.searchfacade.shared.filter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class RangeValidator implements ConstraintValidator<IsRangeValid, Filter.Range> {

    @Override
    public void initialize(IsRangeValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(Filter.Range value, ConstraintValidatorContext context) {
        return value.getFrom() <= value.getTo();
    }
}