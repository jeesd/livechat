package org.mylivedata.app.dashboard.domain.custom;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.mylivedata.app.dashboard.validator.PasswordEquals;

/**
 * Created by lubo08 on 19.3.2014.
 */
@PasswordEquals
public class CreateUserFormDataView implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2123958208507707074L;

	int userId;

    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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


}
