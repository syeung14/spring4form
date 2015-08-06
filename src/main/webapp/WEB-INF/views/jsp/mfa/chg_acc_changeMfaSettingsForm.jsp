<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>

<%-- 
<%
Cookie cookies[] = request.getCookies();
if (cookies != null) {
	for (int i = 0; i < cookies.length; i++) {
		Cookie cookie = cookies[i];
		System.out.println("in 'JSP' cookie: " + cookie.getName() + ": " + cookie.getValue() + " : " + cookie.getDomain() 
			+ " : " + cookie.getPath());
	}
}

%> --%>

<html lang="en">
<head>
<meta charset="utf-8" />
<style>
#mfaSetupView .mfaSetupViewContainer {
	width: 100%;
	box-sizing: border-box;
	padding: 0 20px 20px 20px;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupViewContainerHeader {
	width: 100%;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupViewContainerHeader #mfaSetupViewContainerHeaderText
	{
	color: #FBB450;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer {
	width: 100%;
	box-sizing: border-box;
	border: 1px solid lightgray;
	padding: 20px;
	padding-top: 30px;
	border-radius: 5px;
	box-shadow: 1px 1px 4px #DDD;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerleft
	{
	width: 430px;
	box-sizing: border-box;
	display: inline-block;
	border-right: 1px solid black;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerleft .mfaSetupmethodContainerHeader #mfaSetupmethodContainerHeaderText
	{
	color: black;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerleft .mfaSetupMethods
	{
	width: 100%;
	padding: 10px;
	box-sizing: border-box;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerleft .mfaSetupMethods .mfaSetupMethod
	{
	height: 60px;
	line-height: 60px;
	float: left;
	width: 100%;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerleft .mfaSetupMethods .mfaSetupMethod .mfaSetupMethodBtn
	{
	float: left;
	line-height: 60px;
	height: 60px;
	position: relative;
	width: 45%;
	box-sizing: border-box;
	padding-right: 15px;
}


#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerleft .mfaSetupMethods .mfaSetupMethod .mfaSetupMethodBtn .btnMfaSetupMethods
	{
	height: 50px;
	line-height: 50px;
	padding: 0px 12px;
	width: 100%;
	box-sizing: border-box;
	margin-top: 5px;
	border: 1px solid lightgray;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerleft .mfaSetupMethods .mfaSetupMethod .mfaSetupMethodBtn .btnMfaSetupMethods:hover
	{
	border: 1px solid black;
}



#mfaSetupView .mfaSetupViewContainer .mfaEnterCodeContainer .mfaEnterCodeContainerHeaderForm .mfaSetupMethods .mfaSetupMethod .mfaSetupMethodBtn .btnMfaSetupMethods
	{
	height: 50px;
	line-height: 50px;
	padding: 0px 12px;
	width: 100%;
	box-sizing: border-box;
	margin-top: 5px;
	border: 1px solid lightgray;
}

#mfaSetupView .mfaSetupViewContainer .mfaEnterCodeContainer .mfaEnterCodeContainerHeaderForm .mfaSetupMethods .mfaSetupMethod .mfaSetupMethodBtn .btnMfaSetupMethods:hover
	{
	border: 1px solid black;
}








#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerleft .mfaSetupMethods .mfaSetupMethod .mfaSetupMethodText
	{
	float: left;
	color: gray;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerRight
	{
	width: 330px;
	display: inline-block;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerRight .mfaEnterCodeContainer
	{
	width: 100%;
	box-sizing: border-box;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerRight .mfaEnterCodeContainer .mfaEnterCodeContainerHeader
	{
	text-align: center;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerRight .mfaEnterCodeContainer .mfaEnterCodeContainerHeader #mfaEnterCodeContainerHeaderText
	{
	color: black;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerRight .mfaEnterCodeContainer .mfaEnterCodeContainerHeaderForm
	{
	width: 100%;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerRight .mfaEnterCodeContainer .mfaEnterCodeContainerHeaderForm form
	{
	float: left;
	margin: 0px;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerRight .mfaEnterCodeContainer .mfaEnterCodeContainerHeaderForm form .mfaEnterCodeInputDiv
	{
	text-align: center;
	margin-top: 10px;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerRight .mfaEnterCodeContainer .mfaEnterCodeContainerHeaderForm form .mfaEnterCodeInputDiv input
	{
	width: 250px;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerRight .mfaEnterCodeContainer .mfaEnterCodeContainerHeaderForm form .mfaEnterCodeInputHintDiv
	{
	text-align: center;
	padding: 0 20px;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerRight .mfaEnterCodeContainer .mfaEnterCodeContainerHeaderForm form .mfaEnterCodeCheckboxDiv
	{
	text-align: center;
	padding: 60px 20px 38px 20px;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerRight .mfaEnterCodeContainer .mfaEnterCodeContainerHeaderForm form .mfaEnterCodeSubmitDiv
	{
	text-align: center;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerRight .mfaEnterCodeContainer .mfaEnterCodeContainerHeaderForm form .mfaEnterCodeSubmitDiv .btnCodeSubmit
	{
	height: 30px;
	line-height: 30px;
	padding: 4px 40px;
	background-image: linear-gradient(to bottom, #FBB450, #F89406);
	color: #FFF;
	background-color: #FBB450;
}

#mfaSetupView .mfaSetupViewContainer .mfaSetupmethodContainer .mfaSetupmethodContainerRight .mfaEnterCodeContainer .mfaEnterCodeContainerHeaderForm form .mfaEnterCodeSubmitDiv .btnCodeSubmit:hover
	{
	background-position: 0px 0px;
}
.ia-reset{
	display: inline-block;
	width: 100%;
}
#wide-left{
	width: 100% !important;
	box-sizing: border-box;
}

#main{
	background: #FFF !important;
}
</style>

</head>
<body>
<jsp:include page="../fragments/header.jsp" />

<div id="wide-left">
	<div id="mfaSetupView">
		<div class="mfaSetupViewContainer">
			<div class="mfaSetupViewContainerHeader">
				<h2 id="mfaSetupViewContainerHeaderText">Muti-factor Authentication</h2>
			</div>
			
			<div class="mfaSetupmethodContainer">
				<div class="mfaSetupmethodContainerleft">
					<div class="mfaSetupmethodContainerHeader">
						<h3 id="mfaSetupmethodContainerHeaderText">This Device</h3>
					</div>
					<div class="mfaSetupMethods">
						<div class="mfaSetupMethod">
							<div class="mfaSetupMethodBtn">
								<!-- <a class="btn btnMfaSetupMethods">Forget this device</a> -->
									<spring:url value="/forgetDevice" var="userActionUrl" />
									<form id="mfa_setup_form" method="POST" action="${userActionUrl}" htmlEscape="true">
										<input type="hidden" value="forgetThisOne" name="forgetType"/>	
										<input type="submit" id="forgetThisDevice" value="Forget this device" class="btn btnMfaSetupMethods" />
									</form>
							</div>
							<div class="mfaSetupMethodText">You chose to remember this device in 2015-07-03. </div>
						</div>
					</div>
				</div>
				<div class="mfaSetupmethodContainerRight">
					<div class="mfaEnterCodeContainer">
						<div class="mfaEnterCodeContainerHeader">
							<h3 id="mfaEnterCodeContainerHeaderText">Other Devices</h3>
						</div>
						<div class="mfaEnterCodeContainerHeaderForm">
							<div class="mfaSetupMethods">
								<div class="mfaSetupMethod">
									<div class="mfaSetupMethodBtn">
									<spring:url value="/forgetDevice" var="userActionUrl" />
									<form id="mfa_setup_form" method="POST" action="${userActionUrl}" htmlEscape="true">
										<input type="hidden" value="forgetAll" name="forgetType"/>	
										<input type="submit"  id="forgetAllDevice" value="Forget all the device"  class="btn btnMfaSetupMethods" 
											<c:if test="${empty otherDevices}">
												disabled
											</c:if>/>
									</form>
									</div>
									<div class="mfaSetupMethodText">If you have previously chosen not to be asked for verification codes on other computers, you can forget(reset) them here.</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</div>

<br>watch here : <br>
<p>Not loaded yet.</p>

<a href="https://localhost:8443/sip/main/roster/member.do" > go sip site </a>


	<c:if test="${not empty device}">
   <spring:url value="/delete" var="userActionUrl" /> 
	<form:form class="form-horizontal" method="post" modelAttribute="userForm" action="${userActionUrl}">

		<c:forEach var="dId" items="${device}">
		 <a href="<spring:url value='/delete/${dId}'/>" class="btn btnMfaSetupMethods">	<c:out value="${dId}"/> </a>
		</c:forEach>
	</form:form>
		
	</c:if>


	<c:if test="${not empty device}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>

<%
   Cookie cookie = null;
   Cookie[] cookies = null;
   // Get an array of Cookies associated with this domain
   cookies = request.getCookies();
   if( cookies != null ){
      out.println("<h2> Found Cookies Name and Value</h2>");
      for (int i = 0; i < cookies.length; i++){
         cookie = cookies[i];
         out.print("Name : " + cookie.getName( ) + ",  ");
         out.print("Path : " + cookie.getPath( ) + ",  ");
         out.print("Domain : " + cookie.getDomain( ) + ",  ");
         out.print("Value: " + cookie.getValue( )+" <br/>");
      }
  }else{
      out.println("<h2>No cookies founds</h2>");
  }
%>




<div id="thin-right"></div>
<jsp:include page="../fragments/footer.jsp" />

<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
  
<script>
	function removeDeviceCallback(data) {
		if(data.result){
			$("#forgetThisDevice").attr("disabled", "enabled");
		}
	}		

	$(document).ready(function() {
		$("p").text("The DOM is now loaded and can be manipulated.");
		
		var _url = "https://verification.local.folioidentity.com:7481/verification/mfa/deviceManager";
		$.ajax( {
	        type: "GET",
	        url: _url,
	        data :{
	           whichDeivce : "isCurrentActive"
	        },
	        dataType:"JSONP",
	        xhrFields: {
	            withCredentials: true
	        }
		});
		
	});
</script>
  
</body>
</html>
