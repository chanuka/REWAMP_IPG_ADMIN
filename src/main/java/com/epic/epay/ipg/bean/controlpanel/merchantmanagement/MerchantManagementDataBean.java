/**
 *
 */
package com.epic.epay.ipg.bean.controlpanel.merchantmanagement;

/**
 * @author Asitha Liyanawaduge
 *
 * 19/11/2013
 */
public class MerchantManagementDataBean {

    private String merchantid;
    private String headmerchant;
    private String merchantname;
    private String address;
    private String city;
    private String email;
    private String digitalSign;
    private String filepath;
    private String defaultmerchant;
    private String createdtime;
    private long fullCount;

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getDigitalSign() {
        return digitalSign;
    }

    public void setDigitalSign(String digitalSign) {
        this.digitalSign = digitalSign;
    }

    public String getHeadmerchant() {
        return headmerchant;
    }

    public void setHeadmerchant(String headmerchant) {
        this.headmerchant = headmerchant;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getDefaultmerchant() {
        return defaultmerchant;
    }

    public void setDefaultmerchant(String defaultmerchant) {
        this.defaultmerchant = defaultmerchant;
    }
    

}
