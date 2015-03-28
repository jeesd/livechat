package org.mylivedata.app.dashboard.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.mylivedata.app.dashboard.domain.custom.CreateUserFormDataView;

/**
 * Created by lubo08 on 13.4.2014.
 */
public class PasswordEqualsValidator implements ConstraintValidator<PasswordEquals, Object> {
    @Override
    public void initialize(PasswordEquals passwordEquals) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        CreateUserFormDataView rfdw = (CreateUserFormDataView)o;

        String message = constraintValidatorContext.getDefaultConstraintMessageTemplate();

        //constraintValidatorContext.disableDefaultConstraintViolation();
        ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = constraintValidatorContext.buildConstraintViolationWithTemplate(message);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nbdc = violationBuilder.addPropertyNode("confirmPassword");
        nbdc.addConstraintViolation();


        return rfdw.getPassword().equals(rfdw.getConfirmPassword());
    }
}
