
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="applications" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<spring:message code="application.trip.title" var="viewTitleHeader" />
	<display:column title="${viewTitleHeader}">
		<jstl:if test="${!row.trip.getBanned()}">
			<a href="trip/view.do?tripId=${row.trip.id}">
				<jstl:out value="${row.trip.title}"/>
			</a>
		</jstl:if>
		<jstl:if test="${row.trip.getBanned() }">
				<spring:message code="application.trip.banned" />
			
		</jstl:if>
		
	</display:column>
	
	<!-- Attributes -->
	
	<acme:column sorteable="false" code="application.status" path="status"/>
	
</display:table>

