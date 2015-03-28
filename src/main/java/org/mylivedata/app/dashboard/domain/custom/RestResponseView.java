package org.mylivedata.app.dashboard.domain.custom;

import java.io.Serializable;

/**
 * Created by lubo08 on 26.3.2014.
 */
public class RestResponseView implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6600315942442068562L;
	public enum ResponseCodes {
        OK(0,"OK"),
        NO_OK(1,"Failed");

        private int code;
        private String label;
        private ResponseCodes(int code, String label){
            this.code = code;
            this.label = label;
        }

        public int getCode() {
            return code;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append(code);
            return sb.toString();
        }

    }

    private int responseCode;
    private String responseLabel;
    private String responseMessage;

    public void setResponseCode(int responseCode){
        this.responseCode = responseCode;
    }
    public int getResponseCode(){
        return this.responseCode;
    }
    public void setResponseLabel(String responseLabel){
        this.responseLabel = responseLabel;
    }
    public String getResponseLabel(){
        return this.responseLabel;
    }
    public void setResponseMessage(String responseMessage){
        this.responseMessage = responseMessage;
    }
    public String getResponseMessage(){
        return this.responseMessage;
    }
}

