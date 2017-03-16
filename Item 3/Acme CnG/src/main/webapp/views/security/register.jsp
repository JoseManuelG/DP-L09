 <%--
 * login.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="actorForm">
	

	<acme:textbox code="security.register.username" path="userAccount.username"/>
	
	<acme:password code="security.password" path="userAccount.password"/>
	
	<acme:password code="security.confirm.password" path="confirmPassword"/>
    
    <acme:textbox code="security.register.name" path="name"/>
	
	<acme:textbox code="security.register.surname" path="surname"/>
	
	<acme:textbox code="security.register.email" path="email"/>
	
	<acme:textbox code="security.register.picture" path="picture"/>
	
	<acme:textbox code="security.register.phone" path="phone"/>
	
	<a target="_blank" href="law/terms-conditions.do">
		<spring:message code="security.register.terms" />
	</a>
	
	<br/>
		<acme:checkbox code="security.register.AceptTerms" path="acepted"/>
	
	<acme:submit code="security.register.save" name="save"/>
	
	<acme:cancel url="" code="security.register.cancel"/>
	
</form:form>