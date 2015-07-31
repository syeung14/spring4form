<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
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
			
			<spring:url value="/delete" var="userActionUrl" />
			<form id="mfa_setup_form" method="POST" action="${userActionUrl}" htmlEscape="true">
			<div class="mfaSetupmethodContainer">
				<div class="mfaSetupmethodContainerleft">
					<div class="mfaSetupmethodContainerHeader">
						<h3 id="mfaSetupmethodContainerHeaderText">This Device</h3>
					</div>
					<div class="mfaSetupMethods">
						<div class="mfaSetupMethod">
							<div class="mfaSetupMethodBtn">
								<!-- <a class="btn btnMfaSetupMethods">Forget this device</a> -->
								<input type="hidden" id="deviceId" name="thisDeviceId"
									value="${thisDevice}" />

								<input type="submit" id="forgetThisDevice" value="Forget this device"
									class="btn btnMfaSetupMethods" />
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
										<input type="button" id="forgetAllDevice" value="Forget all the device"  class="btn btnMfaSetupMethods" />
									</div>
									<div class="mfaSetupMethodText">If you have previously chosen not to be asked for verification codes on other computers, you can reset them here.</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			</form>
			
		</div>
	</div>
</div>


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

hello!
<form action="<c:url value="/create"/>" method="post"> 
    <fieldset> 
        <legend>Login</legend> 
        <table> 
        <tr> 
            <td>Username</td> 
            <td> 
                <input type="text" id="username" name="username"  
                        placeholder="Usename"/></td> 
        </tr> 
        <tr> 
            <td>Password</td> 
            <td> 
                <input type="password" id="password" name="password" 
                       placeholder="Password"/></td> 
        </tr> 
        <tr><td colspan="2" align="center"> 
            <button id="login">Login</button> 
        </td></tr> 
        </table> 
    </fieldset> 
</form> 


<div id="thin-right"></div>
<jsp:include page="../fragments/footer.jsp" />
</body>
</html>
