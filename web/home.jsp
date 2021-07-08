<%-- 
    Document   : home
    Created on : Jul 2, 2021, 8:11:35 PM
    Author     : VI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resource Sharing Page</title>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>
    <body>
        <font color="red">
        Welcome, ${sessionScope.USER.name}
        </font>
        <h1>Welcome to my company's resource !</h1>

        <form action="search">
            Search Value: <input type="text" name="txtSearchValue" value="${requestScope['SEARCHVALUE']}" />
            <input type="submit" value="Search" name="btnAction" />
        </form>

        <form action="logout">
            <input type="submit" value="Logout" name="btnAction"/>
        </form>

        <c:if test="${requestScope.SEARCHRESULT != null}">

            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Color</th>
                        <th>Category</th>
                        <th>Quantity</th>
                        <th>Option</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${requestScope['SEARCHRESULT']}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>
                                ${item.itemID}
                                <input type="hidden" name="txtItemID" value="${item.itemID}"/>
                            </td>
                            <td>
                                ${item.itemName}
                                <input type="hidden" name="txtItemName" value="${item.itemName}"/>
                            </td>
                            <td>
                                ${item.color}
                                <input type="hidden" name="txtColor" value="${item.color}"/>
                            </td>
                            <td>
                                ${item.category}
                                <input type="hidden" name="txtCategory" value="${item.category}"/>
                            </td>
                            <td>
                                ${item.quantity}
                                <input type="hidden" name="txtQuantity" value="${item.quantity}"/>
                            </td>
                            <td>

                                <c:if test="${sessionScope.USER.role ne '1'}">
                                    <c:if test="${item.status eq 'New'}">
                                        <form action="book">
                                            <input type="hidden" name="txtItemID" value="${item.itemID}"/>
                                            <input type="submit" value="Book" name="btnAction"/>
                                        </form> 
                                    </c:if>
                                    <c:if test="${item.status eq 'Waiting'}">
                                        <input readonly="" type="text" value="Waiting for approval" name="mess"/>    
                                    </c:if>
                                </c:if>

                                <c:if test="${sessionScope.USER.role eq '1'}">
                                    <form action="accept">
                                        <input type="hidden" name="txtItemID" value="${item.itemID}"/>
                                        <input type="submit" value="Accept" name="btnAction"/>
                                    </form>
                                    <form action="delete">
                                        <input type="hidden" name="txtItemID" value="${item.itemID}"/>
                                        <input type="submit" value="Delete" name="btnAction"/>
                                    </form>
                                </c:if>

                            </td>
                        </tr>
                    </c:forEach>


                </tbody>
            </table>

        </c:if>
        <c:if test="${requestScope.SEARCHRESULT == null}">
            <p style="color: red" >${requestScope.ERROR}</p>
        </c:if>


    </body>
</html>
