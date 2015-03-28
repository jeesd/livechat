package org.mylivedata.app.dashboard.domain.custom;

import java.io.Serializable;

public class AccountFormView implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8033333377503145060L;
	private Integer userId;
    private String billingEmail;
    private String companyName;
    private String streetAddressLine1;
    private String streetAddressLine2;
    private String City;
    private String stateProvince;
    private String zip;
    private String country;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStreetAddressLine2() {
        return streetAddressLine2;
    }

    public void setStreetAddressLine2(String streetAddressLine2) {
        this.streetAddressLine2 = streetAddressLine2;
    }

    public String getStreetAddressLine1() {
        return streetAddressLine1;
    }

    public void setStreetAddressLine1(String streetAddressLine1) {
        this.streetAddressLine1 = streetAddressLine1;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
