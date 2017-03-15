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
<jstl:out value="${res.senderName}"/><br/>

<spring:message code="message.for"/>:
<jstl:out value="${res.recipientName}"/><br/>

<spring:message code="message.sendingMoment"/>:
<jstl:out value="${res.sendingMoment}"/><br/>

<spring:message code="message.title"/>:
<jstl:out value="${res.title}"/><br/>

<fieldset>
	<legend><spring:message code="message.text"/></legend>
	<jstl:out value="${res.text}"/>
</fieldset><br/>

<jstl:if test="${attachments.size()!=0}">
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="attachments" requestURI="${requestURI}" id="row">
		
		<acme:column sorteable="false" code="message.attachment.name" path="name"/>
		
		<display:column>
			<a href="${row.url}">${row.url}</a>
		</display:column>
		
	</display:table>
</jstl:if>

<jstl:if test="${res.sender!=null}">
	<a href="message/actor/reply.do?messageId=${res.id}">
		<spring:message code="message.reply"/>
	</a> | 
</jstl:if>

<a href="message/actor/forward.do?messageId=${res.id}">
	<spring:message code="message.forward"/>
</a> | 

<a href="message/actor/delete.do?messageId=${res.id}">
	<spring:message code="message.delete"/>
</a>