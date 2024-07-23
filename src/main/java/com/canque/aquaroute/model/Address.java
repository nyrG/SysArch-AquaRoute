package com.canque.aquaroute.model;

public class Address {
    private String street;
    private String barangay;
    private String city;
    private String province;

    public Address() {
    }

    public Address(String street, String barangay, String city, String province) {
        this.street = street;
        this.barangay = barangay;
        this.city = city;
        this.province = province;
    }
    
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getBarangay() {
        return barangay;
    }
    public void setBarangay(String barangay) {
        this.barangay = barangay;
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
}
