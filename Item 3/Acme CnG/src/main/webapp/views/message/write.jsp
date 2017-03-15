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


<form:form action="message/actor/write.do" modelAttribute="messageForm">

	<acme:textbox code="message.title" path="title"/>
	<acme:textbox code="message.text" path="text"/>

	<form:label path="recipient"><spring:message code="message.for" /></form:label>	
	<form:select path="recipient">
		<jstl:forEach items="${actors}"  var="actor">
			<form:option value="${actor.id}">
				<jstl:out value="${actor.name} ${actor.surname} (${actor.userAccount.username})"/>
			</form:option>
		</jstl:forEach>
	</form:select>
	<form:errors path="recipient" cssClass="error" />
	
	<br/>
	ATTACHMENTS
	<br/>
	
	<br/>
	<acme:submit code="message.save" name="save" />
	
	<acme:submit code="message.addAttachment" name="addAttachment" />
	
	<jstl:if test="${messageForm.attachments.size()>0}">
		<acme:submit code="message.removeAttachment" name="removeAttachment" />
	</jstl:if>
	
	<acme:cancel code="message.cancel" url="" />
</form:form>