package org.mylivedata.app.dashboard.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.mylivedata.app.dashboard.domain.custom.RegistrationFormDataView;

/**
 * Created by lubo08 on 13.4.2014.
 */
public class UserAgreementAcceptedValidator implements ConstraintValidator<UserAgreementAccepted, Object> {
    @Override
    public void initialize(UserAgreementAccepted userAgreementAccepted) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RegistrationFormDataView rfdw = (RegistrationFormDataView)o;

        String message = constraintValidatorContext.getDefaultConstraintMessageTemplate();

        //constraintValidatorContext.disableDefaultConstraintViolation();
        ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = constraintValidatorContext.buildConstraintViolationWithTemplate(message);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nbdc = violationBuilder.addPropertyNode("agreement");
        nbdc.addConstraintViolation();


        return rfdw.getAgreement();
    }
}
