package com.atlasai.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomerInfo {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private static boolean hasText(String s) {
        return s != null && !s.isBlank();
    }

    @JsonIgnore
    public boolean isValid() {
        return hasText(firstName) && hasText(lastName) && hasText(email);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
