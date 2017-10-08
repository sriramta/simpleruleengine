package org.acme.rule.domain;

/**
 * Created by tasri on 7/10/2017.
 */
public class User {

    private String lineManagerUsrKey;
    private String businessArea1;
    private String businessArea2;
    private String businessArea3;
    private String businessArea4;
    private String country;
    private String city;
    private String region;

    public String getLineManagerUsrKey() {
        return lineManagerUsrKey;
    }

    public void setLineManagerUsrKey(String lineManagerUsrKey) {
        this.lineManagerUsrKey = lineManagerUsrKey;
    }

    public String getBusinessArea1() {
        return businessArea1;
    }

    public void setBusinessArea1(String businessArea1) {
        this.businessArea1 = businessArea1;
    }

    public String getBusinessArea2() {
        return businessArea2;
    }

    public void setBusinessArea2(String businessArea2) {
        this.businessArea2 = businessArea2;
    }

    public String getBusinessArea3() {
        return businessArea3;
    }

    public void setBusinessArea3(String businessArea3) {
        this.businessArea3 = businessArea3;
    }

    public String getBusinessArea4() {
        return businessArea4;
    }

    public void setBusinessArea4(String businessArea4) {
        this.businessArea4 = businessArea4;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
