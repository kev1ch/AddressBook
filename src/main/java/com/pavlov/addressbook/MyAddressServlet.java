package com.pavlov.addressbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyAddressServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        AddressDB.load();
    }

    @Override
    public void destroy() {
        AddressDB.save();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String operation = request.getParameter("operation");
        if (operation.equals("add")) {
            doAddAddress(request, response);
        } else if (operation.equals("delete")) {
            doDeleteAddress(request, response);
        }
    }

    private void doAddAddress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String address_line = request.getParameter("address_line");
        String city = request.getParameter("city");
        String zip = request.getParameter("zip");
        String phone = request.getParameter("phone");
        String state = request.getParameter("state");
        String country = request.getParameter("country");
        String web_string;
        try {
            Address new_address = new Address(name, address_line, city, zip, phone, state, country);
            AddressDB.add(new_address);
            web_string = constructWebPage("add", "added an address: " + new_address);
        } catch (AddressException exception) {
            String message = exception.getMessage();
            web_string = constructWebPage("error", message);
        }
        sendResponse(response, web_string);
    }
    
    /**
     * Deletes an address by id
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void doDeleteAddress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("address_id");
        int int_id = Integer.parseInt(id);
        AddressDB.remove(int_id);
        String web_string = constructWebPage("delete", "DEL#" + id);
        sendResponse(response, web_string);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String list_of_addresses = "";
        List<Address> address_list = AddressDB.getAll();
        for (Address address : address_list) {
            list_of_addresses = list_of_addresses + address + "<br>";
        }
        String web_string = constructWebPage("get", "list of addresses: <br>" + list_of_addresses);
        sendResponse(response, web_string);
    }
    
    private String constructWebPage(String operation, String content) {
        String result = "<html> <head> <title> %s </title> </head> <body>"
                + "<h1>%s</h1><br>%s <hr> <a href=\"javascript:history.go(-1)\">Back to main page</a> </body> </html>";
        String title = "";
        if (operation.equals("get")) {
            title = "print all addresses";
        } else if (operation.equals("add")) {
            title = "add information";
        } else if (operation.equals("delete")) {
            title = "remove address";
        } else {
            title = operation;
        }
        result = String.format(result, title, title, content);
        return result;
    }
    
    private void sendResponse(HttpServletResponse response, String content)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(content);
        }
    }
    
}
