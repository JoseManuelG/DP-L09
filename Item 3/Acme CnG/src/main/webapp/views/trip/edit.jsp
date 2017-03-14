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

<form:form action="trip/customer/edit.do" modelAttribute="trip">
	
	<acme:textbox code="trip.title" path="title"/>
	<acme:textbox code="trip.description" path="description"/>
	<acme:textbox code="trip.origin" path="origin"/>
	<acme:textbox code="trip.originLat" path="originLat"/>
	<acme:textbox code="trip.originLon" path="originLon"/>
	<acme:textbox code="trip.destination" path="origin"/>
	<acme:textbox code="trip.destinationLat" path="destinationLat"/>
	<acme:textbox code="trip.destinationLon" path="destinationLon"/>
	<acme:textbox code="trip.departureTime" path="departureTime" placeholder="dd/mm/aaaa hh:mm"/>
	
	
	<acme:submit code="trip.save" name="save" />
	
	<acme:cancel code="trip.cancel" url="" />
</form:form>