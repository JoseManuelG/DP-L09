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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<spring:message  code="attachment.audit" />: <jstl:out value="${attachment.audit}" />
<br>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="attachments" requestURI="attachment/view.do" id="row">
	
	<!-- Attributes -->

	<spring:message code="attachment.name" var="attachmentHeader" />
	<display:column property="name" title="${attachmentHeader}" sortable="true" />

	<spring:message code="attachment.url" var="urlHeader" />
	<display:column  title="${urlHeader}" sortable="true" >
		<a href="<jstl:out value="${row.url}"/>"><jstl:out value="${row.url}"/></a>
	</display:column>

</display:table>
<br>

	