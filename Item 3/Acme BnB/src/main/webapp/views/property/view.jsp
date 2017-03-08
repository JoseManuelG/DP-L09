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

<spring:message  code="property.name" />: <jstl:out value="${property.name}" />
<br>
<spring:message  code="property.rate" />: <jstl:out value="${property.rate}"/>
<br>
<spring:message  code="property.description" />: <jstl:out value="${property.description}"/>
<br>
<spring:message  code="property.address" />: <jstl:out value="${property.address}"/>
<br>
<spring:message  code="property.lastUpdate" />: <jstl:out value="${property.lastUpdate}"/>
<br>

<h2><spring:message  code="property.attributes" />:</h2>

<jstl:if test="${!results.isEmpty()}">
	<display:table pagesize="5" excludedParams="*" class="displaytag" name="attributeValues"  uid="attributeValue" requestURI="${requestURI}">
		
		<!-- Action links -->
		<jstl:if test="${ esMiProperty}">
		<display:column>
			<a href="attributeValue/lessor/edit.do?attributeValueId=${attributeValue.id}">
				<spring:message	code="attributeValue.edit" />
			</a>
	
		</display:column>
			</jstl:if>
		<!-- Attributes -->
		
		<acme:column sorteable="false" code="attribute.name" path="attribute.name"/>
		
		<acme:column sorteable="false" code="attributeValue.value" path="value"/>
		
	</display:table>
			<jstl:if test="${ esMiProperty}">
			<a href=attributeValue/lessor/create.do?propertyId=${property.id}>
	      <spring:message  code="attributeValue.create" />
	</a>
			</jstl:if>
</jstl:if>

<h2><spring:message  code="property.audits" />:</h2>
<display:table pagesize="5" excludedParams="*" class="displaytag" name="audits"  uid="audit" requestURI="${requestURI}">
		
		<!-- Action links -->
		
		<spring:message code="property.view.audit" var="auditHeader" />
			<display:column title="${auditHeader}">
			<a href="audit/view.do?auditId=${audit.id}">
				<spring:message	code="audit.view" />
			</a>
	
		</display:column>
			
		<!-- Attributes -->
		
		<acme:column sorteable="false" code="audit.text" path="text"/>
		
		<acme:column sorteable="false" code="audit.auditor.name" path="auditor.name"/>
		
		<acme:column sorteable="false" code="audit.auditor.companyName" path="auditor.companyName"/>
		
	</display:table>

<security:authorize access="hasRole('AUDITOR')">
		<jstl:if test="${auditorTieneAudit}">
			<a href="audit/auditor/create.do?propertyId=${property.id}">
				<spring:message	code="audit.create" />
			</a>
		</jstl:if>
		</security:authorize>
	