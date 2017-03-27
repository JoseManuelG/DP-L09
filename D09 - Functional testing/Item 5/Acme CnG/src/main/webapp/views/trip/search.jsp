<%--
 *
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="trip/customer/search.do" modelAttribute="searchForm">

	<p><spring:message code="trip.search.text"/></p>

	<form:label path="type">
		<spring:message code="trip.type" />
	</form:label>
	<form:select path="type">
		<form:option value="offers"><spring:message code="trip.search.offer"/></form:option>
		<form:option value="requests"><spring:message code="trip.search.request"/></form:option>
	</form:select>
	<form:errors path="type"/>
	<br>
	
	<acme:textbox code="trip.search.keyword" path="keyword"/>
	
	<acme:submit code="trip.search" name="search" />
	<acme:cancel code="trip.cancel" url="" />
	
</form:form>