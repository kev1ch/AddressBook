package com.pavlov.addressbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddressDB {
    
    private static final String FILENAME = "C:\\temp\\addressbook.txt";
    
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
    
    public static void save() {
        System.out.println("saved to: " + FILENAME);
        File data_file = new File(FILENAME);
        try (OutputStream output_stream = new FileOutputStream(data_file, false)) {
            for (int i = 0; i < address_list.size(); i++) {
                Address address = address_list.get(i);
                String address_line = String.format("%d;%s;%s;%s;%s;%s;%s;%s\n",
                        address.getID(), address.getName(), address.getAddressLine(),
                        address.getCity(), address.getZip(), address.getPhone(),
                        address.getState(), address.getCountry());
                byte[] address_line_bytes = address_line.getBytes("UTF-8");
                output_stream.write(address_line_bytes);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IOException ex) {
            System.out.println("Input/Output error");
        }
    }
    
    public static void load() {
        System.out.println("loaded from: " + FILENAME);
        Path filename_path = Paths.get(FILENAME);
        try {
            List<String> lines = Files.readAllLines(filename_path);
            address_list.clear();
            for (int i = 0; i < lines.size(); i++) {
                String address_string = lines.get(i);
                String[] address_fields = address_string.split(";");
                int address_fields_length = address_fields.length;
                try {
                    Address loaded_address = new Address(address_fields[1], address_fields[2],
                                                     address_fields[3], address_fields[4],
                                                     address_fields_length > 5 ? address_fields[5] : "",
                                                     address_fields_length > 6 ? address_fields[6] : "",
                                                     address_fields_length > 7 ? address_fields[7] : "");
                    int id = Integer.parseInt(address_fields[0]);
                    loaded_address.setID(id);
                    address_list.add(loaded_address);
                } catch (AddressException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AddressDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
