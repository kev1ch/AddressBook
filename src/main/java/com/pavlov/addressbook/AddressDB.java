package com.pavlov.addressbook;

import java.util.ArrayList;
import java.util.List;

public class AddressDB {
    
    private static final List<Address> address_list = new ArrayList<>();
    
    public static void add(Address address) {
        int available_id = getAvailableID();
        address.setID(available_id);
        address_list.add(address);
    }
    
    public static void modify(Address address) {
        
    }
    
    public static void remove(int id) {
        for (int i = 0; i < address_list.size(); i++) {
            if (address_list.get(i).getID() == id) {
                address_list.remove(i);
                break;
            }
        }
    }
    
    public static List<Address> getAll() {
        ArrayList<Address> clone_list = (ArrayList<Address>) address_list;
        return (List<Address>) clone_list.clone();
    }
    
    public static int getAvailableID() {
        int max_id = 0;
        for (int i = 0; i < address_list.size(); i++) {
            Address address = address_list.get(i);
            max_id = max_id > address.getID() ? max_id : address.getID();
        }
        return max_id + 1;
    }
    
}
