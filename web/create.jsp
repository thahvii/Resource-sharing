<%-- 
    Document   : create
    Created on : Jul 3, 2021, 9:41:12 PM
    Author     : VI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Page</title>
    </head>
    <body>
        <h1>Create new account</h1>
        <form action="create">

            <tr>
                <td>User ID*</td>
                <td><input required="" type="text" name="txtUserID" value=""/></td><br>
            </tr>
            <tr>
                <td>Password*</td> 
                <td><input required="" type="password" name="txtPassword" value=""/></td><br>
            </tr>
            <tr>
                <td>Name*</td>
                <td><input required="" type="text" name="txtName" value=""/></td><br>
            </tr>
            <tr>
                <td>Phone*</td>
                <td><input required="" type="text" name="txtPhone" value=""/></td><br>
            </tr>
            <tr>
                <td>Address*</td>
                <td><input required="" type="text" name="txtAddress" value=""/></td><br>
            </tr>


            <c:if test="${not empty requestScope.EXIST}">
                <p style="color: red" >${requestScope.EXIST}</p>
            </c:if>
            <br></br>

            <div class="g-recaptcha" 
                 data-sitekey="6LfbP-YaAAAAANMKR8ez_E2EcaxzSL5V2wKLt4Ou"></div>
            <td>
                <input type="submit" value="Create" name="btnAction" />
        </form>
    </td>
<td>
    <input type="reset" value="Reset" />
</td>
<!-- reCAPTCHA with Vietnamese language -->
<script src='https://www.google.com/recaptcha/api.js?hl=vi'></script>

</body>
</html>
