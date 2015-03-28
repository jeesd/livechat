package org.mylivedata.app.dashboard.domain.custom;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Marian on 11/12/2014.
 */
public class ProfileFormView implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6570674835234412268L;
	private Integer userId;
    private String name;
    private String surrname;

    @NotEmpty
    @Email
    private String email;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurrname() {
        return surrname;
    }

    public void setSurrname(String surrname) {
        this.surrname = surrname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
