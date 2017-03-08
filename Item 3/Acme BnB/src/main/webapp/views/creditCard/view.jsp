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

<jstl:if test="${editable ne false}">
	<security:authorize access="hasRole('LESSOR')">
	<spring:message  code="creditCard.holderName" />: <jstl:out value="${creditCard.holderName}" />
	<br>
	<spring:message  code="creditCard.brandName" />: <jstl:out value="${creditCard.brandName}"/>
	<br>
	<spring:message  code="creditCard.number" />: <jstl:out value="${creditCard.number}"/>
	<br>
	<spring:message  code="creditCard.expirationMonth" />: <jstl:out value="${creditCard.expirationMonth}"/>
	<br>
	<spring:message  code="creditCard.expirationYear" />: <jstl:out value="${creditCard.expirationYear}"/>
	<br>
	<spring:message  code="creditCard.cvvCode" />: <jstl:out value="${creditCard.cvvCode}"/>
	<br>

	<a href="creditCard/lessor/edit.do">
		      <spring:message  code="creditCard.edit" />
	</a>
	</security:authorize>
</jstl:if>

<jstl:if test="${editable eq false}">
	<security:authorize access="hasRole('LESSOR')">
		<spring:message  code="creditCard.noCreditCard" />
		<a href="creditCard/lessor/create.do">
			      <spring:message  code="creditCard.newCreditCard" />
		</a>
	</security:authorize>
</jstl:if>
	