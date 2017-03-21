
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
	name="trips" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<spring:message code="trip.view.title" var="viewTitleHeader" />
	<display:column title="${viewTitleHeader}">
			<a href="trip/view.do?tripId=${row.id}">
				<spring:message	code="trip.view" />
			</a>
	</display:column>
<%-- 	<jstl:if test="${requestURI == 'trip/list.do'}"> --%>
	    <spring:message code="trip.view.customer" var="viewTitleHeader" />
	    <display:column title="${viewTitleHeader}">
	    <jstl:if test="${ row.customer.id ne null}">
		<a href="actor/view.do?actorId=${row.customer.id}">
	      <spring:message  code="trip.view" />
	   	  </a>
	   	 </jstl:if>
		<jstl:if test="${ row.customer.id eq null}"> 
		<spring:message  code="trip.customer.noAvalaible" />
		</jstl:if>
	    </display:column>
<%--   	</jstl:if> --%>

	<!-- Attributes -->
	
	<acme:column sorteable="false" code="trip.title" path="title" highlight="${row.banned}"/>
	<acme:column sorteable="false" code="trip.origin" path="origin" highlight="${row.banned}"/>
	<acme:column sorteable="false" code="trip.destination" path="destination" highlight="${row.banned}"/>
	<acme:column sorteable="false" code="trip.departureTime" path="departureTime" highlight="${row.banned}"/>
	
	<security:authorize access="hasRole('CUSTOMER')">	
		<spring:message code="trip.apply.title" var="applyTitleHeader" />
		<display:column title="${applyTitleHeader}">
			<a href="application/customer/apply.do?tripId=${row.id}">
				<spring:message	code="trip.apply" />
			</a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">	
		<display:column>
			<jstl:choose>
				<jstl:when test="${!row.banned}">
					<a href="trip/administrator/ban.do?tripId=${row.id}">
						<spring:message	code="trip.ban" />
					</a>
				</jstl:when>
				<jstl:otherwise>
					<a href="trip/administrator/unban.do?tripId=${row.id}">
						<spring:message	code="trip.unban" />
					</a>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('CUSTOMER')">
	<a href="trip/customer/create/offer.do">
	      <spring:message  code="trip.new.offer" />
	</a>
</security:authorize>
&nbsp;
<security:authorize access="hasRole('CUSTOMER')">
	<a href="trip/customer/create/request.do">
	      <spring:message  code="trip.new.request" />
	</a>
</security:authorize>

