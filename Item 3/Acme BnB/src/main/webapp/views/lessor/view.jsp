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


<spring:message code="lessor.name" />
:
<jstl:out value="${lessor.name}" />
<br>
<spring:message code="lessor.surname" />
:
<jstl:out value="${lessor.surname}" />
<br>
<spring:message code="lessor.email" />
:
<jstl:out value="${lessor.email}" />
<br>
<spring:message code="lessor.phone" />
:
<jstl:out value="${lessor.phone}" />
<br>
<acme:image url="${lessor.picture}"/>
<br>
<jstl:if test="${esMiPerfil}">
			<a href=security/edit.do>
	      <spring:message  code="property.edit" />
	</a>
	<br>
</jstl:if>
<br>
<h2><spring:message  code="lessor.properties" />:</h2>
<jstl:if test="${!properties.isEmpty()}">
	<display:table pagesize="5" class="displaytag" name="properties"
		requestURI="${requestURI}" id="row" uid="property">

		<!-- Action links -->
		<jstl:if test="${esMiPerfil}">
			<spring:message code="property.edit.property" var="editHeader" />
			<display:column title="${editHeader}">
				<a href="property/lessor/edit.do?propertyId=${property.id}">
				<spring:message	code="property.edit" />
				</a>
				
			</display:column>
		</jstl:if>
		<!-- Attributes -->

		<acme:column sorteable="true" code="finder.property.name" path="name" />

		<acme:column sorteable="true" code="finder.property.rate" path="rate" />

		<acme:column sorteable="false" code="finder.property.description"
			path="description" />

		<acme:column sorteable="false" code="finder.property.address"
			path="address" />

		<display:column>
			<a href="property/view.do?propertyId=${property.id}"> <spring:message
					code="finder.display" />
			</a>
		</display:column>

	</display:table>
</jstl:if>
<jstl:if test="${esMiPerfil}">
			<a href=property/lessor/create.do>
	      <spring:message  code="lessor.property.create" />
	</a>
</jstl:if>
<br>
<br>
<h2><spring:message  code="lessor.socialIdentities" />:</h2>
<jstl:if test="${!socialIdentities.isEmpty()}">
	<display:table pagesize="5" class="displaytag1" name="socialIdentities"
		requestURI="${requestURI}" id="row" uid="social">

		<!-- Action links -->
		<jstl:if test="${esMiPerfil}">
			<spring:message code="socialIdentity.edit" var="editHeader" />
			<display:column title="${editHeader}">
				<a href="socialIdentity/edit.do?socialIdentityId=${social.id}">
				<spring:message	code="property.edit" />
				</a>
				
			</display:column>
		</jstl:if>
		<!-- Attributes -->

		<acme:column sorteable="true" code="socialIdentity.nick" path="nick" />

		<acme:column sorteable="true" code="socialIdentity.socialNetwork"
			path="socialNetwork" />

		<display:column>
			<a href=<jstl:out value="${social.link}"/> target="_parent"> <spring:message
					code="socialIdentity.link" />
			</a>
		</display:column>

	</display:table>
</jstl:if>
<jstl:if test="${esMiPerfil}">
			<a href=socialIdentity/create.do>
	      <spring:message  code="socialIdentity.create" />
	</a>
</jstl:if>
<br>
<br>
<h2><spring:message  code="lessor.comments" />:</h2>
<jstl:if test="${!comments.isEmpty()}">
	<display:table pagesize="5" class="displaytag1" name="comments"
		requestURI="${requestURI}" id="row" uid="comments">

		<!-- Action links -->

		<!-- Attributes -->

		<acme:column sorteable="true" code="lessor.comment.title" path="title" />

		<acme:column sorteable="true" code="lessor.comment.text"
			path="text" />

		<acme:column sorteable="true" code="lessor.comment.stars"
			path="stars" />
		<acme:column sorteable="true" code="lessor.comment.postMoment"
			path="postMoment" />


	</display:table>
	</jstl:if>
<jstl:if test="${puedoComentar}">
	<a href="comment/customer/create.do?customerId=${lessor.id }">
	      <spring:message  code="comment.create" />
	</a>
</jstl:if>