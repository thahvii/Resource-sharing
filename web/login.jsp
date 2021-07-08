<%-- 
    Document   : login
    Created on : Jun 30, 2021, 5:21:30 PM
    Author     : VI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Login Page</h1>
        <form action="login" method="post">
            <table>
                <tr>
                    <td>User ID:</td>
                    <td><input  required="Please input UserID " type="text" name="txtUserID" value=""/></td><br>
                </tr>
                <tr>
                    <td>Password:</td> 
                    <td><input required="Please input password" type="password" name="txtPassword" value=""/></td><br>
                </tr>

                <c:if test="${not empty requestScope.ERROR}">
                    <p style="color: red" >${requestScope.ERROR}</p>
                </c:if>

            </table> <br></br>
            <div class="g-recaptcha"
                 data-sitekey="6LfbP-YaAAAAANMKR8ez_E2EcaxzSL5V2wKLt4Ou"></div>

            <td colspan="2"><input type="submit" value="Login"name="btnAction" /></td>

            <td colspan="2"><input type="reset" value="Reset" />

        </form> <br>

        <a href="create.jsp">Create new account</a>

        <!-- reCAPTCHA with Vietnamese language -->
        <script src='https://www.google.com/recaptcha/api.js?hl=vi'></script>

    </form>
</body>
</html>
