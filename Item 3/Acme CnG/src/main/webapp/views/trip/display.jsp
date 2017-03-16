<%--
 * action-1.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
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
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<jstl:if test="${isOwner && trip.banned}">
	<spring:message code="trip.banned"/>
	<br>
</jstl:if>
<jstl:if test="${trip.customer != null}">
	<a href="customer/view.do?customerId=${trip.customer.id}">
	<jstl:out value="${trip.customer.name}"/>&nbsp;<jstl:out value="${trip.customer.surname}"/>
	</a>
</jstl:if>
<jstl:if test="${trip.customer == null}">
		<spring:message code="trip.customer.deleted"/>
</jstl:if>
<br>
<spring:message  code="trip.title" />: <jstl:out value="${trip.title}" />
<br>
<spring:message  code="trip.description" />: <jstl:out value="${trip.description}"/>
<br>
<spring:message  code="trip.origin" />: <jstl:out value="${trip.origin}"/>
<br>
<jstl:if test="${trip.originLat != null}">
	<spring:message  code="trip.originLat" />: <jstl:out value="${trip.originLat}"/>
	<br>
	<spring:message  code="trip.originLon" />: <jstl:out value="${trip.originLon}"/>
	<br>
</jstl:if>
<spring:message  code="trip.destination" />: <jstl:out value="${trip.destination}"/>
<br>
<jstl:if test="${trip.destinationLat != null}">
	<spring:message  code="trip.destinationLat" />: <jstl:out value="${trip.destinationLat}"/>
	<br>
	<spring:message  code="trip.destinationLon" />: <jstl:out value="${trip.destinationLon}"/>
	<br>
</jstl:if>
<spring:message  code="trip.departureTime" />: <jstl:out value="${trip.departureTime}"/>
<br>

<security:authorize access="hasRole('CUSTOMER')">	
	<a href="trip/customer/apply.do?tripId=${trip.id}">
		<spring:message	code="trip.apply" />
	</a>
</security:authorize>
			
<jstl:if test="${isOwner && !aplications.isEmpty()}">

<h2><spring:message  code="trip.applications" />:</h2>
	<display:table pagesize="5" excludedParams="*" class="displaytag" 
	name="aplications"  uid="aplication" requestURI="${requestURI}">
		
		<display:column>
		    <a href="customer/view.do?customerId=${row.customer.id}">
		    <spring:message code="trip.view" />
		   	</a>
	   	</display:column>
		<acme:column sorteable="false" code="trip.application.status" path="status"/>
		
		<spring:message code="trip.application.accept.title" var="acceptTitleHeader" />
		<display:column title="${acceptTitleHeader}">
			<a href="trip/customer/accept/application.do?applicationId=${row.id}">
				<spring:message	code="trip.application.accept" />
			</a>
		</display:column>
		
		<spring:message code="trip.application.deny.title" var="denyTitleHeader" />
		<display:column title="${denyTitleHeader}">
			<a href="trip/customer/deny/application.do?applicationId=${row.id}">
				<spring:message	code="trip.application.deny" />
			</a>
		</display:column>
		
	</display:table>
</jstl:if>
	