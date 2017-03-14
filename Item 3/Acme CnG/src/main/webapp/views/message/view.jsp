<%--
 * index.jsp
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
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<spring:message code="message.from"/>:
<jstl:out value="${message.senderName}"/><br/>

<spring:message code="message.for"/>:
<jstl:out value="${message.recipientName}"/><br/>

<spring:message code="message.sendingMoment"/>:
<jstl:out value="${message.sendingMoment}"/><br/>

<spring:message code="message.title"/>:
<jstl:out value="${message.title}"/><br/>

<fieldset>
	<legend><spring:message code="message.text"/></legend>
	<jstl:out value="${message.text}"/>
</fieldset><br/>

<jstl:if test="${message.sender!=null}">
	<a href="message/actor/reply.do?messageId=${row.id}">
		<spring:message code="message.reply"/>
	</a> | 
</jstl:if>

<a href="message/actor/forward.do?messageId=${row.id}">
	<spring:message code="message.forward"/>
</a> | 

<a href="message/actor/delete.do?messageId=${row.id}">
	<spring:message code="message.delete"/>
</a>