<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : Error page
    Created on : Feb 21, 2020, 9:59:13 AM
    Author     : viettqhe130524
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./public/css/error-page.css" />
        <link rel="stylesheet" type="text/css" href="./public/css/style.css" />
        <title>Error</title>
    </head>
    <body>
        <div>
            <div class="font-sans text-blue">
                <%@include file="/components/header.jsp" %>
                <!--Content-->
                <div class="flex container">
                    <div class="left pa-5">
                        <!--Show message error-->
                        <p class="error">${error}</p>
                    </div>
                </div>
                <!--Footer-->
                <footer class="footer">
                </footer>
            </div>
        </div>
    </body>
</html>
