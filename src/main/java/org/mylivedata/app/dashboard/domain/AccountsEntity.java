package org.mylivedata.app.dashboard.domain;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Created by lubo08 on 9.3.2014.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "accounts", schema = "", catalog = "mylivedata")
public class AccountsEntity {
    private Integer accountId;
    private Integer ownerId;
    private String billingEmail;
    private String companyName;
    private String streetAdrressLine1;
    private String streetAddressLine2;
    private String city;
    private String stateProvince;
    private String zip;
    private String country;
    private String phone;
    private String registrationCode;
    private UsersEntity usersByOwnerId;
    private Collection<DepartmentsEntity> departmentsesByAccountId;
    private Collection<DomainsEntity> domainsesByAccountId;
    private Collection<UsersEntity> usersesByAccountId;
    private int isVeriefied;
    private String accountIdentity;

    @Id
    @GeneratedValue
    @Column(name = "account_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "owner_id", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Basic
    @Column(name = "billing_email", nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    @Basic
    @Column(name = "company_name", nullable = true, insertable = true, updatable = true, length = 500, precision = 0)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "street_adrress_line_1", nullable = true, insertable = true, updatable = true, length = 1000, precision = 0)
    public String getStreetAdrressLine1() {
        return streetAdrressLine1;
    }

    public void setStreetAdrressLine1(String streetAdrressLine1) {
        this.streetAdrressLine1 = streetAdrressLine1;
    }

    @Basic
    @Column(name = "street_address_line_2", nullable = true, insertable = true, updatable = true, length = 1000, precision = 0)
    public String getStreetAddressLine2() {
        return streetAddressLine2;
    }

    public void setStreetAddressLine2(String streetAddressLine2) {
        this.streetAddressLine2 = streetAddressLine2;
    }

    @Basic
    @Column(name = "City", nullable = true, insertable = true, updatable = true, length = 500, precision = 0)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "state_province", nullable = true, insertable = true, updatable = true, length = 500, precision = 0)
    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    @Basic
    @Column(name = "zip", nullable = true, insertable = true, updatable = true, length = 20, precision = 0)
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Basic
    @Column(name = "country", nullable = true, insertable = true, updatable = true, length = 500, precision = 0)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "phone", nullable = true, insertable = true, updatable = true, length = 50, precision = 0)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "registration_code", nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountsEntity that = (AccountsEntity) o;

        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        if (billingEmail != null ? !billingEmail.equals(that.billingEmail) : that.billingEmail != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (ownerId != null ? !ownerId.equals(that.ownerId) : that.ownerId != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (registrationCode != null ? !registrationCode.equals(that.registrationCode) : that.registrationCode != null)
            return false;
        if (stateProvince != null ? !stateProvince.equals(that.stateProvince) : that.stateProvince != null)
            return false;
        if (streetAddressLine2 != null ? !streetAddressLine2.equals(that.streetAddressLine2) : that.streetAddressLine2 != null)
            return false;
        if (streetAdrressLine1 != null ? !streetAdrressLine1.equals(that.streetAdrressLine1) : that.streetAdrressLine1 != null)
            return false;
        if (zip != null ? !zip.equals(that.zip) : that.zip != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountId != null ? accountId.hashCode() : 0;
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        result = 31 * result + (billingEmail != null ? billingEmail.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (streetAdrressLine1 != null ? streetAdrressLine1.hashCode() : 0);
        result = 31 * result + (streetAddressLine2 != null ? streetAddressLine2.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (stateProvince != null ? stateProvince.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (registrationCode != null ? registrationCode.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "owner_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)})
    public UsersEntity getUsersByOwnerId() {
        return usersByOwnerId;
    }

    public void setUsersByOwnerId(UsersEntity usersByOwnerId) {
        this.usersByOwnerId = usersByOwnerId;
    }

    @OneToMany(mappedBy = "accountsByAccountId")
    public Collection<DepartmentsEntity> getDepartmentsesByAccountId() {
        return departmentsesByAccountId;
    }

    public void setDepartmentsesByAccountId(Collection<DepartmentsEntity> departmentsesByAccountId) {
        this.departmentsesByAccountId = departmentsesByAccountId;
    }

    @OneToMany(mappedBy = "accountsByAccountId")
    public Collection<DomainsEntity> getDomainsesByAccountId() {
        return domainsesByAccountId;
    }

    public void setDomainsesByAccountId(Collection<DomainsEntity> domainsesByAccountId) {
        this.domainsesByAccountId = domainsesByAccountId;
    }

    @OneToMany(mappedBy = "accountsByAccountId")
    public Collection<UsersEntity> getUsersesByAccountId() {
        return usersesByAccountId;
    }

    public void setUsersesByAccountId(Collection<UsersEntity> usersesByAccountId) {
        this.usersesByAccountId = usersesByAccountId;
    }

    @Basic
    @Column(name = "is_veriefied", nullable = true, insertable = true, updatable = true)
    public int getIsVeriefied() {
        return isVeriefied;
    }

    public void setIsVeriefied(int isVeriefied) {
        this.isVeriefied = isVeriefied;
    }

    @Basic
    @Column(name = "account_identity", nullable = true, insertable = true, updatable = true, length = 255)
    public String getAccountIdentity() {
        return accountIdentity;
    }

    public void setAccountIdentity(String accountIdentity) {
        this.accountIdentity = accountIdentity;
    }
}
