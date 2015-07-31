<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
	width: 530px;
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
				<h2 id="mfaSetupViewContainerHeaderText">Setup Muti-factor Authentication</h2>
			</div>
			<div class="mfaSetupmethodContainer">
				<div class="mfaSetupmethodContainerleft">
					<div class="mfaSetupmethodContainerHeader">
						<h3 id="mfaSetupmethodContainerHeaderText">Choose Multi-Factor Authentication Method</h3>
					</div>
					<div class="mfaSetupMethods">
						<div class="mfaSetupMethod">
							<div class="mfaSetupMethodBtn">
								<a class="btn btnMfaSetupMethods">Email</a>
							</div>
							<div class="mfaSetupMethodText">Send an email to Jxxxxxx@xxxx.com</div>
						</div>
						<div class="mfaSetupMethod">
							<div class="mfaSetupMethodBtn">
								<a class="btn btnMfaSetupMethods">Google Authenticator</a>
							</div>
							<div class="mfaSetupMethodText">Use my App to get a new
								code</div>
						</div>

						<div class="mfaSetupMethod">
							<div class="mfaSetupMethodBtn">
								<a class="btn btnMfaSetupMethods">SMS (Text Message)</a>
							</div>
							<div class="mfaSetupMethodText">Send a text message to
								(xxx) xxx-9566</div>
						</div>

						<div class="mfaSetupMethod">
							<div class="mfaSetupMethodBtn">
								<a class="btn btnMfaSetupMethods">Call Me</a>
							</div>
							<div class="mfaSetupMethodText">Call (xxx) xxx-9566</div>
						</div>
					</div>
				</div>
				<div class="mfaSetupmethodContainerRight">
					<div class="mfaEnterCodeContainer">
						<div class="mfaEnterCodeContainerHeader">
							<h3 id="mfaEnterCodeContainerHeaderText">Enter Multi-Factor
								Code</h3>
						</div>
						<div class="mfaEnterCodeContainerHeaderForm">
							<form method="post">
								<div class="mfaEnterCodeInputDiv">
									<input type="text" width="150" />
								</div>

								<div class="mfaEnterCodeInputHintDiv">
									<small>Enter the 6-digit code from your email, Google
										Authenticator, SMS, or phone call below </small>
								</div>

								<div class="mfaEnterCodeCheckboxDiv">
									<input type="checkbox" name="loginIdCookie.include" value="Y"
										tabindex="3" /> <small>This is my personal computer
										(remember this device)</small>
								</div>

								<div class="mfaEnterCodeSubmitDiv"><a class="btn btn-success btnCodeSubmit">Submit and Login Again</a></div>
							</form>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</div>

	<c:if test="${not empty device}">
		<Strong>${device.deviceId }</Strong>
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

<div id="thin-right"></div>
<jsp:include page="../fragments/footer.jsp" />
</body>
</html>
