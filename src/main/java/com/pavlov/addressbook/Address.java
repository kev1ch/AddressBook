package com.pavlov.addressbook;

import java.io.Serializable;

public class Address implements Serializable {
    
    private int id;
    
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

    public void setID(int id) {
        this.id = id;
    }
    
    public int getID() {
        return this.id;
    }
    
    @Override
    public String toString() {
        String result = String.format("ID: %d, Name: %s, Address Line: %s, City: %s, " +
                                      "Zip: %s", id, name, address_line, city, zip);
        return result;
    }
    
    
    
}
