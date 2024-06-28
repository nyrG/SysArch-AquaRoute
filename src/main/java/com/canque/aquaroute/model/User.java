package com.canque.aquaroute.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User {

    private String email;
    private String password;

    private String firstName;
    private String lastName;
    private String middleName;
    private String birthdate;

    private String street;
    private String barangay;
    private String city;
    private String province;

    public User(){

    }

    public User(String firstName, String lastName, String middleName, String birthdate, String street,
            String barangay, String city, String province, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthdate = birthdate;
        this.street = street;
        this.barangay = barangay;
        this.city = city;
        this.province = province;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    } 
    
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getbarangay() {
        return barangay;
    }
    public void setbarangay(String barangay) {
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
