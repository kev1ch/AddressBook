package com.pavlov.addressbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyAddressServlet extends HttpServlet {

    //private static final List<Address> address_list = new ArrayList<>();
    
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
        ServletContext context = request.getSession().getServletContext();
        List<Address> address_list = (List)context.getAttribute("address_list");
        if (address_list == null) {
            address_list = new ArrayList<>();
        }
        String name = request.getParameter("name");
        String address_line = request.getParameter("address_line");
        String city = request.getParameter("city");
        String zip = request.getParameter("zip");
        Address new_address = new Address(name, address_line, city, zip);
        address_list.add(new_address);
        context.setAttribute("address_list", address_list);
        String web_string = constructWebPage("add", "added an address: " + new_address);
        sendResponse(response, web_string);
    }
    
    private void doDeleteAddress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getSession().getServletContext();
        List<Address> address_list = (List)context.getAttribute("address_list");
        String id = request.getParameter("address_id");
        int int_id = Integer.parseInt(id);
        address_list.remove(int_id);
        String web_string = constructWebPage("delete", "removed address #" + id);
        sendResponse(response, web_string);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext session = request.getSession().getServletContext();
        List<Address> address_list = (List)session.getAttribute("address_list");
        if (address_list == null) {
            address_list = new ArrayList<>();
        }
        String list_of_addresses = "";
        int counter = 0;
        for (Address address : address_list) {
            list_of_addresses = list_of_addresses + counter + ": " + address + "<br>";
            counter++;
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
