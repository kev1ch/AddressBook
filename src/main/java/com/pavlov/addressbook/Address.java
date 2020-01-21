package com.pavlov.addressbook;

import java.io.Serializable;

public class Address implements Serializable {
    
    private String name;
    private String address_line;
    private String city;
    private String zip;
    
    public Address(String name, String address_line, String city, String zip) {
        this.name = name;
        this.address_line = address_line;
        this.city = city;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public String getAddressLine() {
        return address_line;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    @Override
    public String toString() {
        String result = String.format("Name: %s, Address Line: %s, City: %s, " +
                                      "Zip: %s", name, address_line, city, zip);
        return result;
    }
    
    
    
}
