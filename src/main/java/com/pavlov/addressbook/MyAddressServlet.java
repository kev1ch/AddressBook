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
        } else if (operation.equals("modify")) {
            doModifyAddress(request, response);
        }
    }

    private void doModifyAddress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String address_line = request.getParameter("address_line");
        String city = request.getParameter("city");
        String zip = request.getParameter("zip");
        String phone = request.getParameter("phone");
        String state = request.getParameter("state");
        String country = request.getParameter("country");
        String web_string;
        try {
            Address modify_address = new Address(name, address_line, city, zip, phone, state, country);
            modify_address.setID(Integer.parseInt(id));
            AddressDB.modify(modify_address);
            web_string = constructWebPage("modify", "modified an address: " + modify_address,
                                                                   request.getContextPath());
        } catch (AddressException exception) {
            String message = exception.getMessage();
            web_string = constructWebPage("error", message, request.getContextPath());
        }
        sendResponse(response, web_string);
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
            web_string = constructWebPage("add", "added an address: " + new_address,
                                                          request.getContextPath());
        } catch (AddressException exception) {
            String message = exception.getMessage();
            web_string = constructWebPage("error", message, request.getContextPath());
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
        String id = request.getParameter("delete");
        int int_id = Integer.parseInt(id);
        AddressDB.remove(int_id);
        String web_string = constructWebPage("delete", "DEL#" + id, request.getContextPath());
        sendResponse(response, web_string);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String edit_parameter = request.getParameter("edit");
        String delete_parameter = request.getParameter("delete");
        if (edit_parameter != null) {
            modifyAddress(request, response);
        } else if (delete_parameter != null) {
            doDeleteAddress(request, response);
        } else {
            showAddressList(request, response);
        }
        showAddressList(request, response);
    }
    
    private void showAddressList(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        StringBuilder list_of_addresses = new StringBuilder("<table border=1>");
        list_of_addresses.append("<tr><th>ID</th><th>Name</th><th>Address Line</th>"
                + "<th>City</th><th>Zip</th><th>Phone</th><th>State</th><th>Country</th>"
                + "<th></th><th></th></tr>");
        List<Address> address_list = AddressDB.getAll();
        for (Address address : address_list) {
            list_of_addresses.append("<tr>");
            list_of_addresses.append("<td>");
            list_of_addresses.append(address.getID());
            list_of_addresses.append("</td>");
            list_of_addresses.append("<td>");
            list_of_addresses.append(address.getName());
            list_of_addresses.append("</td>");
            list_of_addresses.append("<td>");
            list_of_addresses.append(address.getAddressLine());
            list_of_addresses.append("</td>");
            list_of_addresses.append("<td>");
            list_of_addresses.append(address.getCity());
            list_of_addresses.append("</td>");
            list_of_addresses.append("<td>");
            list_of_addresses.append(address.getZip());
            list_of_addresses.append("</td>");
            list_of_addresses.append("<td>");
            list_of_addresses.append(address.getPhone());
            list_of_addresses.append("</td>");
            list_of_addresses.append("<td>");
            list_of_addresses.append(address.getState());
            list_of_addresses.append("</td>");
            list_of_addresses.append("<td>");
            list_of_addresses.append(address.getCountry());
            list_of_addresses.append("</td>");
            list_of_addresses.append("<td>");
            list_of_addresses.append("<a href='?edit=" + address.getID() + "'>Edit</a>");
            list_of_addresses.append("</td>");
            list_of_addresses.append("<td>");
            list_of_addresses.append("<a href='?delete=" + address.getID() + "'>Remove</a>");
            list_of_addresses.append("</td>");
            list_of_addresses.append("</tr>");
        }
        list_of_addresses.append("</table>");
        String web_string = constructWebPage("get", "list of addresses: <br>" + list_of_addresses,
                                                                        request.getContextPath());
        sendResponse(response, web_string);
    }
    
    private void modifyAddress(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String address_id = request.getParameter("edit");
        Address modify_address = AddressDB.getByID(address_id);
        StringBuilder modify_form = new StringBuilder("");
        modify_form.append("<form method=\"POST\" action=\"addressbook\">");
        modify_form.append("<input type=\"hidden\" name=\"operation\" value=\"modify\">");
        modify_form.append("<input type=\"hidden\" name=\"id\" value=\"" + address_id + "\">");
        modify_form.append("<table>");
        modify_form.append("<tr><td>Name:</td><td><input type=\"text\" name=\"name\""
                + "value='" + modify_address.getName() + "'></td></tr>");
        modify_form.append("<tr><td>Address Line:</td><td><input type=\"text\" name=\"address_line\""
                + "value='" + modify_address.getAddressLine() +"'></td></tr>");
        modify_form.append("<tr><td>City:</td><td><input type=\"text\" name=\"city\""
                + "value='" + modify_address.getCity() + "'></td></tr>");
        modify_form.append("<tr><td>Zip:</td><td><input type=\"text\" name=\"zip\""
                + "value='" + modify_address.getZip() + "'></td></tr>");
        modify_form.append("<tr><td>Phone:</td><td><input type=\"text\" name=\"phone\""
                + "value='" + modify_address.getPhone() + "'></td></tr>");
        modify_form.append("<tr><td>State or Province:</td><td><input type=\"text\" name=\"state\""
                + "value='" + modify_address.getState() + "'></td></tr>");
        modify_form.append("<tr><td>Country:</td><td><input type=\"text\" name=\"country\""
                + "value='" + modify_address.getCountry() + "'></td></tr>");
        modify_form.append("</table>");
        modify_form.append("<input type=\"submit\" value=\"Modify Address\">");
        modify_form.append("</form>");
        String web_string = constructWebPage("modify", "modify address:<br>" + modify_form,
                                                                 request.getContextPath());
        sendResponse(response, web_string);
    }
    
    private String constructWebPage(String operation, String content, String servlet_context) {
        String result = "<html> <head> <title> %s </title> </head> <body>"
                + "<h1>%s</h1><br>%s <hr> <a href=\"" + servlet_context +
                                "\">Back to main page</a> </body> </html>";
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
