<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"><spring:message code="label.title" /> </a>
			<span class="pull-right"> <a
				class="navbar-brand" href="logout">Logout </a>
			</span>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="label.addComputer" /></h1>
					<form:form action="addComputer" method="POST" id="submit" onsubmit = "return validateDates()" modelAttribute="computerDto">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message code="label.computerName" /> *</label> <form:input
									type="text" class="form-control required" id="computerName"
									name="computerName" placeholder="Computer name" path="name"/>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="label.introduced" /></label> <form:input
									type="date" class="form-control" id="introduced"
									name="introduced" placeholder="Introduced date" path="introduced"/>
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="label.discontinued" /></label> <form:input
									type="date" class="form-control" id="discontinued"
									name="discontinued" placeholder="Discontinued date" path="discontinued"/>
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="label.company" /></label> <form:select
									class="form-control" id="companyId" name="companyId" path="companyId">
									<option value="0">--</option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}"><c:out
												value="${company.id} - ${company.name}" /></option>
									</c:forEach>
								</form:select>
							</div>
							<i>* <spring:message code="label.required" /></i>
						</fieldset>
						<div class="actions pull-right">
						<spring:message code="addComputer.add" var="add" />
							<input type="submit" value="${add}" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default"><spring:message code="label.cancel" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="./js/validateDates.js"></script>
</body>
</html>