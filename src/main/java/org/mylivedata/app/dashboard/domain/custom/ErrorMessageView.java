package org.mylivedata.app.dashboard.domain.custom;

import java.io.Serializable;

/**
 * Created by lubo08 on 25.10.2014.
 */
public class ErrorMessageView implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8769258928295140771L;
	int errorCode;
    String errorMessage;
    String forwardUrl;
    String buttonText;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

}
