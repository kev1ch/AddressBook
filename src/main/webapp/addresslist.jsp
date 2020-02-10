<%-- 
    Document   : addresslist
    Created on : 10 февр. 2020 г., 17:34:24
    Author     : Daniil
--%>

<%@page import="java.util.List"%>
<%@page import="com.pavlov.addressbook.AddressDB"%>
<%@page import="com.pavlov.addressbook.Address"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New JSP addresslist</title>
    </head>
    <% if (!AddressDB.isLoaded()) {
            AddressDB.load();
       }
        
       List<Address> address_list = AddressDB.getAll();
    %>
    <body>
        <h1>AddressList</h1>
        <table border="1">
        <% for (int i = 0; i < address_list.size(); i++) {%>
        <tr><td>Line #<%=i+1%></td><td><%=address_list.get(i)%></td></tr> <br>
        <% } %>
        </table>
    </body>
</html>
