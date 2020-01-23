<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" 
integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link rel="stylesheet" href="style.css">
</head>
<body>

<c:if test="${sessionScope.uname == null}">
	<%-- user is not available --%>
	<c:redirect url="index.jsp"></c:redirect>
</c:if>


<c:if test="${sessionScope.uname !=null && sessionScope.role == 1}">
<div class="row" style="background-color:lightgreen;  height:50px; text-align: center; padding:10px;">
<div class="col-5"></div>
<div class="col-3"></div>
<div class="col-2"></div>
<div class="col-1"><a href="logout.htm"><h3>Logout</h3></a></div>
</div>
<div class="login-page">
    <div class="form">
    <form class="Bidding Details">
        <div class="col-3"></div>
        <div class="col-6" style="font-size:20px;">Bidding Details</div> 
        <div class="col-3"><br></div> 
        <input type="text" placeholder="Bid-Id"/>
        <input type="text" placeholder="Project-Id"/>
        <input type="text" placeholder="Company-Id"/>
        <input type="text" placeholder="StartUp-Id"/>
        <input type="text" placeholder="Bid Amount"/>
        <input type="text" placeholder="Bid-Duration"/>
        <input type="text" placeholder="Bid-Status"/>
        <input type="submit"/>
        
    </form>
    </div>
    </div>
    </c:if>
</body>
</html>