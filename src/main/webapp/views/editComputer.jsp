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
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> <spring:message code="label.title" /> </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${computerDto.id}</div>
					<h1><spring:message code="editComputer.edit" /></h1>
					<c:if test="${error!=null}">
						<div class="alert alert-danger" role="alert">${error}</div>
					</c:if>
					<c:if test="${sucess!=null}">
						<div class="alert alert-success" role="alert">
							<h4>${sucess}</h4>
							<p>
								<a href="dashboard" class="btn btn-primary pull-right">Go back to
									dashboard</a>
							</p>
						</div>
					</c:if>
					<form:form action="editComputer?idComputer=${computerDto.id}"
						method="POST" onsubmit="return validateDates()" modelAttribute="computerDto">
						<form:input type="hidden" id="id" name="id" path="id" value="${computerDto.id}"   />
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message code="label.computerName" /></label> <form:input
									type="text" class="form-control " id="computerName"
									placeholder="Computer name" name="computerName"
									value="${computerDto.name}"   path="name"/>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="label.introduced" /></label> <form:input
									type="date" class="form-control" id="introduced"
									name="introduced" placeholder="Introduced date"
									value="${computerDto.introduced}" path="introduced"/>
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="label.discontinued" /></label> <form:input
									type="date" class="form-control" id="discontinued"
									name="discontinued" placeholder="Discontinued date"
									value="${computerDto.discontinued}" path="discontinued"/>
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="label.company" /></label> <form:select
									class="form-control" id="companyId" name="companyId" path="companyId">
									<option value="0">--</option>
									<c:forEach items="${companies}" var="company">
										<c:set var="selected" value="" />
										<c:if test="${company.id == computerDto.company.id}">
											<c:set var="selected" value="selected" />
										</c:if>
										<option value="${company.id}" ${selected}><c:out
												value="${company.id} - ${company.name}" /></option>
									</c:forEach>
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
						<spring:message code="label.edit" var="edit" />
							<input type="submit" value="${edit}" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default"><spring:message code="label.cancel" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="./js/validateEdits.js"></script>
</body>
</html>