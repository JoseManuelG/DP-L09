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
	<a href="actor/view.do?actorId=${trip.customer.id}">
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
	<a href="application/customer/apply.do?tripId=${trip.id}">
		<spring:message	code="trip.apply" />
	</a>
</security:authorize>
			
<jstl:if test="${isOwner && !aplications.isEmpty()}">

<h2><spring:message  code="trip.applications" />:</h2>
	<display:table pagesize="5" excludedParams="*" class="displaytag" 
	name="applications" uid="apptable" requestURI="${requestURI}">
		
		<display:column>
		    <a href="actor/view.do?actorId=${apptable.customer.id}">
		    <spring:message code="trip.view" /></a>
	   	</display:column>
		<acme:column sorteable="false" code="trip.application.status" path="status"/>
		
		<display:column>
			<jstl:if test="${apptable.status eq 'PENDING'}">
				<a href="application/customer/accept.do?applicationId=${apptable.id}">
				<spring:message	code="trip.application.accept" /></a> |
				<a href="application/customer/deny.do?applicationId=${apptable.id}">
				<spring:message	code="trip.application.deny" /></a>
			</jstl:if>
		</display:column>
		
	</display:table>
</jstl:if>

<h2><spring:message  code="actor.comments" />:</h2>
<jstl:if test="${!unBannedComments.isEmpty()}">
	<display:table pagesize="5" class="displaytag1" name="unBannedComments"
		requestURI="${requestURI}" id="row" uid="unBannedComments">

		<!-- Action links -->
		<jstl:if test="${isAdmin}">
	   	  <display:column title=" ">
	    	  <a href="comment/ban.do?commentId=${unBannedComments.id}">
	   		  <spring:message code="actor.comment.banComment"/>
	  	 	  </a>
	  	  </display:column>
	    </jstl:if>
		<spring:message code="actor.comment.name" var="actorName" />
	    <display:column title="${actorName}">
	      <a href="actor/view.do?actorId=${unBannedComments.actor.id}">
	   	  <jstl:out value="${unBannedComments.actor.name}"/>
	   	  <jstl:out value="${unBannedComments.actor.surname}"/>
	   	  </a>
	    </display:column>
		<!-- Attributes -->

		<acme:column sorteable="true" code="actor.comment.title" path="title" />

		<acme:column sorteable="true" code="actor.comment.text"
			path="text" />

		<acme:column sorteable="true" code="actor.comment.stars"
			path="stars" />
		<acme:column sorteable="true" code="actor.comment.postMoment"
			path="postMoment" />


	</display:table>
</jstl:if>
	<a href="comment/create.do?commentableId=${trip.id }">
	      <spring:message  code="comment.create" />
	</a>
	

<jstl:if test="${!bannedComments.isEmpty()&& !isAdmin}">
<h2><spring:message  code="actor.bannedComments" />:</h2>
	<display:table pagesize="5" class="displaytag1" name="bannedComments"
		requestURI="${requestURI}" id="row" uid="bannedComments">

		<!-- Action links -->
		<spring:message code="actor.comment.name" var="actorName" />
	    <display:column title="${actorName}">
	      <a href="actor/view.do?actorId=${bannedComments.commentable.id}">
	   	  <jstl:out value="${bannedComments.actor.name}"/>
	   	  <jstl:out value="${bannedComments.actor.surname}"/>
	   	  </a>
	    </display:column>
		<!-- Attributes -->
		<acme:column sorteable="true" code="actor.comment.title" path="title" />

		<acme:column sorteable="true" code="actor.comment.text"
			path="text" />

		<acme:column sorteable="true" code="actor.comment.stars"
			path="stars" />
		<acme:column sorteable="true" code="actor.comment.postMoment"
			path="postMoment" />

	</display:table>
</jstl:if>
<jstl:if test="${!allBannedComments.isEmpty()&& isAdmin}">
<h2><spring:message  code="actor.bannedComments" />:</h2>
	<display:table pagesize="5" class="displaytag1" name="allBannedComments"
		requestURI="${requestURI}" id="row" uid="allBannedComments">

		<!-- Action links -->
		<display:column title=" ">
	   		<a href="comment/disBan.do?commentId=${allBannedComments.id}">
	   		 <spring:message code="actor.comment.disBanComment"/>
	  	 	</a>
	  	</display:column>
		<spring:message code="actor.comment.name" var="actorName" />
	    <display:column title="${actorName}">
	      <a href="actor/view.do?actorId=${allBannedComments.commentable.id}">
	   	  <jstl:out value="${allBannedComments.actor.name}"/>
	   	  <jstl:out value="${allBannedComments.actor.surname}"/>
	   	  </a>
	    </display:column>
		<!-- Attributes -->
		<acme:column sorteable="true" code="actor.comment.title" path="title" />

		<acme:column sorteable="true" code="actor.comment.text"
			path="text" />

		<acme:column sorteable="true" code="actor.comment.stars"
			path="stars" />
		<acme:column sorteable="true" code="actor.comment.postMoment"
			path="postMoment" />

	</display:table>
</jstl:if>