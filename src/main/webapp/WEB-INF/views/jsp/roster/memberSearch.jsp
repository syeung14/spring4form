<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="../fragments/header.jsp" />
<body>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
 
<form method="GET" action="<c:url value="/member/search"/>"> 
    <fieldset> 
        <legend>Search Criteria</legend> 
        <table> 
            <tr> 
                <td><label for="lastName">Last Name</label></td> 
                <td><input id="lastName" name="lastName"/></td> 
                <td><label for="firstName">First Name</label></td> 
                <td><input id="firstName" name="firstName"/></td> 
                <td><input id="age" name="age"/></td> 
                <td><input type="hidden" id="deviceId" name="deviceId" value="222222222"/></td> 
            </tr> 
        </table> 
    </fieldset> 
    <button id="search">Search</button> 
</form> 
 
<c:if test="${not empty memberList}">
	<p id="firstName">${memberList.firstName}</p>  
	<p id="lastName">${memberList.lastName}</p>  
	<p id="deviceId"> <a href="https://localhost:8443/sip/main/roster/passData.do?deviceId=${memberList.deviceId}"> ${memberList.deviceId} </a></p>  
	<p id="age">${memberList.age}</p>
	<c:url value="/resources/images/books/${memberList.deviceId}/book_front_cover.png" var="bookImage"/>
	   
	image file path: <br/>
	<p> ${bookImage} </p>
	
	
<%--     <table> 
        <tr><th>Title</th><th>Description</th><th>Price</th></tr> 
        <c:forEach items="${memberList}" var="member"> 
            <tr> 
                <td>${member.firstName}</td> 
                <td>${member.lastName}</td> 
            </tr> 
        </c:forEach> 
    </table>  --%>
</c:if> 
<jsp:include page="../fragments/footer.jsp" />
</body>
</html>