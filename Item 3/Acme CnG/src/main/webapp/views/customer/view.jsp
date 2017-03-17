<%--
 * action-1.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<spring:message code="customer.name" />
:
<jstl:out value="${customer.name}" />
<br>
<spring:message code="customer.surname" />
:
<jstl:out value="${customer.surname}" />
<br>
<spring:message code="customer.email" />
:
<jstl:out value="${customer.email}" />
<br>
<spring:message code="customer.phone" />
:
<jstl:out value="${customer.phone}" />
<br>
<h2><spring:message  code="customer.comments" />:</h2>
<jstl:if test="${!unBannedComments.isEmpty()}">
	<display:table pagesize="5" class="displaytag1" name="unBannedComments"
		requestURI="${requestURI}" id="row" uid="unBannedComments">

		<!-- Action links -->

		<!-- Attributes -->

		<acme:column sorteable="true" code="customer.comment.title" path="title" />

		<acme:column sorteable="true" code="customer.comment.text"
			path="text" />

		<acme:column sorteable="true" code="customer.comment.stars"
			path="stars" />
		<acme:column sorteable="true" code="customer.comment.postMoment"
			path="postMoment" />


	</display:table>
</jstl:if>
	<a href="comment/create.do?commentableId=${customer.id }">
	      <spring:message  code="comment.create" />
	</a>
	

<jstl:if test="${!bannedComments.isEmpty()&& !isAdmin}">
<h2><spring:message  code="customer.bannedComments" />:</h2>
	<display:table pagesize="5" class="displaytag1" name="bannedComments"
		requestURI="${requestURI}" id="row" uid="bannedComments">

		<!-- Action links -->

		<!-- Attributes -->
		<acme:column sorteable="true" code="customer.comment.title" path="title" />

		<acme:column sorteable="true" code="customer.comment.text"
			path="text" />

		<acme:column sorteable="true" code="customer.comment.stars"
			path="stars" />
		<acme:column sorteable="true" code="customer.comment.postMoment"
			path="postMoment" />

	</display:table>
</jstl:if>
<jstl:if test="${!allBannedComments.isEmpty()&& isAdmin}">
<h2><spring:message  code="customer.bannedComments" />:</h2>
	<display:table pagesize="5" class="displaytag1" name="allBannedComments"
		requestURI="${requestURI}" id="row" uid="allBannedComments">

		<!-- Action links -->

		<!-- Attributes -->
		<acme:column sorteable="true" code="customer.comment.title" path="title" />

		<acme:column sorteable="true" code="customer.comment.text"
			path="text" />

		<acme:column sorteable="true" code="customer.comment.stars"
			path="stars" />
		<acme:column sorteable="true" code="customer.comment.postMoment"
			path="postMoment" />

	</display:table>
</jstl:if>