package com.pavlov.addressbook;

import java.io.Serializable;

public class Address implements Serializable {
    
    private int id;
    
    private String name;
    private String address_line;
    private String city;
    private String zip;
    private String phone;
    private String state;
    private String country;
    
    public Address(String name, String address_line, String city, String zip,
           String phone, String state, String country)throws AddressException {
        verifyFields(name, address_line, city, zip);
        this.name = name;
        this.address_line = address_line;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.state = state;
        this.country = country;
    }

    private void verifyFields(String name, String address_line, String city, String zip)
            throws AddressException {
        if (name == null || name.isBlank()) {
            throw new AddressException("Input a name please. This is a required field.");
        }
        if (address_line == null || address_line.isBlank()) {
            throw new AddressException("Input an address please. This is a required field.");
        }
        if (city == null || city.isBlank()) {
            throw new AddressException("Input a city please. This is a required field.");
        }
        if (zip == null || zip.isBlank()) {
            throw new AddressException("Input a zip please. This is a required field.");
        }
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

    public String getPhone() {
        return phone;
    }
    
    public String getState() {
        return state;
    }
    
    public String getCountry() {
        return country;
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
                                      "Zip: %s, Phone: %s, State/Province: %s, Country: %s",
                                      id, name, address_line, city, zip, phone, state, country);
        return result;
    }
    
    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object instanceof Address) {
            Address address_object = (Address)object;
            result = address_object.getAddressLine().equalsIgnoreCase(address_line) &&
                address_object.getCity().equalsIgnoreCase(city) &&
                address_object.getZip().equalsIgnoreCase(zip);
        }
        return result;
    }
    
}
