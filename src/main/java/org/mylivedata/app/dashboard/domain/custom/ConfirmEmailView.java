package org.mylivedata.app.dashboard.domain.custom;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class ConfirmEmailView  implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @NotEmpty
	@Email
    private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
