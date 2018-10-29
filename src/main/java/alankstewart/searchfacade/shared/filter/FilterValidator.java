package alankstewart.searchfacade.shared.filter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class FilterValidator implements ConstraintValidator<IsFilterValid, Filter> {

    @Override
    public void initialize(IsFilterValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(Filter value, ConstraintValidatorContext context) {
        if ((value.getValue() == null && value.getRange() == null) || (value.getValue() != null && value.getRange() != null)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Value or range is required")
                    .addConstraintViolation();
            return false;
        }

        if (value.getOperator() != null) {
            switch (value.getOperator()) {
                case gte:
                case lte:
                    if (value.getRange() != null) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate("Range is not permitted with either gte or lte operators")
                                .addConstraintViolation();
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }

        return true;
    }
}
