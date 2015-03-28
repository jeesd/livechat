package org.mylivedata.app.dashboard.domain.custom;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.mylivedata.app.dashboard.validator.UserAgreementAccepted;

/**
 * Created by lubo08 on 19.3.2014.
 */
@UserAgreementAccepted
public class RegistrationFormDataView implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6188141000856514174L;
	private String name;
    @NotEmpty
    @Email
    private String email;

    private String password;

    private String confirmPassword;

    private boolean agreement = false;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean getAgreement() {
        return agreement;
    }

    public void setAgreement(boolean agreement) {
        this.agreement = agreement;
    }
}
