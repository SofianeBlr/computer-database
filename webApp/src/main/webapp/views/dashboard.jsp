<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="label.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<c:set var="searchValue" value="" />
	<c:if test="${search != null}">
		<c:set var="searchValue" value="&search=${search}" />
	</c:if>
	<c:set var="nbPerPage" value="" />
	<c:if test="${numberPerPage != null}">
		<c:set var="nbPerPage" value="&numberPerPage=${numberPerPage}" />
	</c:if>
	<c:set var="orderByValue" value="" />
	<c:if test="${orderBy != null}">
		<c:set var="orderByValue" value="&orderBy=${orderBy}" />
	</c:if>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"><spring:message
					code="label.title" /> </a> <span class="pull-right"> <a
				class="navbar-brand" href="logout">Logout </a>
			</span>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${numberOfComputer}
				<spring:message code="dashboard.computerFound" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" value="${search}" />
						<spring:message code="dashboard.filter" var="valSubmit"></spring:message>
						<input type="submit" id="searchsubmit" value="${valSubmit}"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message
							code="label.addComputer" /> </a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message
							code="label.edit" /> </a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th
							onclick="window.location.href='?orderBy=${orderBy=='cnASC'?'cnDSC': 'cnASC'}${searchValue}${nbPerPage}'">
							<spring:message code="label.computerName" /> <c:if
								test="${orderBy == 'cnASC'}">
								<span class="pull-right">⬆</span>
							</c:if> <c:if test="${orderBy == 'cnDSC'}">
								<span class="pull-right">⬇</span>
							</c:if> <c:if test="${orderBy != 'cnASC'&& orderBy != 'cnDSC'}">
								<span class="pull-right">⬆⬇</span>
							</c:if>
						</th>
						<th
							onclick="window.location.href='?orderBy=${orderBy=='diASC'?'diDSC': 'diASC'}${searchValue}${nbPerPage}'">
							<spring:message code="label.introduced" /> <c:if
								test="${orderBy == 'diASC'}">
								<span class="pull-right">⬆</span>
							</c:if> <c:if test="${orderBy == 'diDSC'}">
								<span class="pull-right">⬇</span>
							</c:if> <c:if test="${orderBy != 'diASC'&& orderBy != 'diDSC'}">
								<span class="pull-right">⬆⬇</span>
							</c:if>
						</th>
						<!-- Table header for Discontinued Date -->
						<th
							onclick="window.location.href='?orderBy=${orderBy=='ddASC'?'ddDSC': 'ddASC'}${searchValue}${nbPerPage}'">
							<spring:message code="label.discontinued" /> <c:if
								test="${orderBy == 'ddASC'}">
								<span class="pull-right">⬆</span>
							</c:if> <c:if test="${orderBy == 'ddDSC'}">
								<span class="pull-right">⬇</span>
							</c:if> <c:if test="${orderBy != 'ddASC'&& orderBy != 'ddDSC'}">
								<span class="pull-right">⬆⬇</span>
							</c:if>
						</th>
						<!-- Table header for Company -->
						<th
							onclick="window.location.href='?orderBy=${orderBy=='ciASC'?'ciDSC': 'ciASC'}${searchValue}${nbPerPage}'"><spring:message
								code="label.company" /> <c:if test="${orderBy == 'ciASC'}">
								<span class="pull-right">⬆</span>
							</c:if> <c:if test="${orderBy == 'ciDSC'}">
								<span class="pull-right">⬇</span>
							</c:if> <c:if test="${orderBy != 'ciASC'&& orderBy != 'ciDSC'}">
								<span class="pull-right">⬆⬇</span>
							</c:if></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computers}" var="computer" varStatus="status">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a href="editComputer?idComputer=${computer.id}"
								onclick=""><c:out value="${computer.name}" /></a></td>
							<td><c:out value="${computer.introduced}" /></td>
							<td><c:out value="${computer.discontinued}" /></td>
							<td><c:out value="${computer.company.name}" /></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</section>
	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<c:if test="${maxPage > 0}">
				<ul class="pagination">
					<c:if test="${currentPage > 1}">
						<li class="page-item"><a
							href="dashboard?page=1${searchValue}${nbPerPage}${orderByValue}"
							aria-label="First"> <span aria-hidden="true">&laquo;&laquo;</span></a>
						</li>
						<li class="page-item"><a
							href="dashboard?page=${currentPage-1}${searchValue}${nbPerPage}${orderByValue}"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a>
						</li>
					</c:if>
					<c:forEach var="i"
						begin="${navMaxPageIndex-4>0?navMaxPageIndex-4:1}"
						end="${navMaxPageIndex}" step="1">
						<c:set var="activePage" value="" />
						<c:if test="${i == currentPage}">
							<c:set var="activePage" value="active" />
						</c:if>
						<li class="page-item ${activePage}"><a
							href="dashboard?page=${i}${searchValue}${nbPerPage}${orderByValue}"><c:out
									value="${i}" /></a></li>
					</c:forEach>
					<c:if test="${currentPage < maxPage}">
						<li class="page-item"><a
							href="dashboard?page=${currentPage+1}${searchValue}${nbPerPage}${orderByValue}"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
						<li class="page-item"><a
							href="dashboard?page=${maxPage}${searchValue}${nbPerPage}${orderByValue}"
							aria-label="Last"> <span aria-hidden="true">&raquo;&raquo;</span></a>
						</li>
					</c:if>
				</ul>
			</c:if>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<c:set var="activeNumber" value="" />
				<c:if test="${numberPerPage == 10}">
					<c:set var="activeNumber" value="active" />
				</c:if>
				<button type="button" class="btn btn-default ${activeNumber}"
					onclick="window.location.href='?numberPerPage=10&search=${search}${orderByValue}'">10</button>
				<c:set var="activeNumber" value="" />
				<c:if test="${numberPerPage == 50}">
					<c:set var="activeNumber" value="active" />
				</c:if>
				<button type="button" class="btn btn-default ${activeNumber}"
					onclick="window.location.href='?numberPerPage=50&search=${search}${orderByValue}'">50</button>
				<c:set var="activeNumber" value="" />
				<c:if test="${numberPerPage == 100}">
					<c:set var="activeNumber" value="active" />
				</c:if>
				<button type="button" class="btn btn-default ${activeNumber}"
					onclick="window.location.href='?numberPerPage=100&search=${search}${orderByValue}'">100</button>
			</div>
			<div class="btn-group btn-group-sm pull-left" role="group">
				<button type="button" id="en" class="btn btn-default align-middle"
					onclick="window.location.href='?${nbPerPage}${searchValue}${orderByValue}&lang=en'">EN</button>
				<button type="button" id="fr" class="btn btn-default align-middle"
					onclick="window.location.href='?${nbPerPage}${searchValue}${orderByValue}&lang=fr'">FR</button>
			</div>
		</div>
	</footer>
	<script src="./js/jquery.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/dashboard.js"></script>


</body>
</html>