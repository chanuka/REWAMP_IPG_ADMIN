/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.bean.util.security;

/**
 * Date 2014-10-02
 *
 * @author Asela Indika
 */
public class KeystoreCreatorBean {

    // for certificate 
    private String name;
    private String organizationalUnitName;
    private String organization;
    private String city;
    private String province;
    private String two_letter_countrycode;

    private String alias;
    private String aliaspassword;
    private String certificatepath;
    private String keystorepassword;
    private String keystorepath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationalUnitName() {
        return organizationalUnitName;
    }

    public void setOrganizationalUnitName(String organizationalUnitName) {
        this.organizationalUnitName = organizationalUnitName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTwo_letter_countrycode() {
        return two_letter_countrycode;
    }

    public void setTwo_letter_countrycode(String two_letter_countrycode) {
        this.two_letter_countrycode = two_letter_countrycode;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAliaspassword() {
        return aliaspassword;
    }

    public void setAliaspassword(String aliaspassword) {
        this.aliaspassword = aliaspassword;
    }

    public String getCertificatepath() {
        return certificatepath;
    }

    public void setCertificatepath(String certificatepath) {
        this.certificatepath = certificatepath;
    }

    public String getKeystorepassword() {
        return keystorepassword;
    }

    public void setKeystorepassword(String keystorepassword) {
        this.keystorepassword = keystorepassword;
    }

    public String getKeystorepath() {
        return keystorepath;
    }

    public void setKeystorepath(String keystorepath) {
        this.keystorepath = keystorepath;
    }

    
}
