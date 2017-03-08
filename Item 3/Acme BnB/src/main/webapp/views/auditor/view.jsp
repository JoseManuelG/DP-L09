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


<spring:message  code="lessor.name" />: <jstl:out value="${auditor.name}" />
<br>
<spring:message  code="lessor.surname" />: <jstl:out value="${auditor.surname}"/>
<br>
<spring:message  code="lessor.email" />: <jstl:out value="${auditor.email}"/>
<br>
<spring:message  code="lessor.phone" />: <jstl:out value="${auditor.phone}"/>
<br>
<acme:image url="${auditor.picture}"/>
<br>
<spring:message  code="auditor.companyName" />: <jstl:out value="${auditor.companyName}"/>
<br>
<a href="security/edit.do"><spring:message  code="auditor.edit" /></a>
<br>
<h2><spring:message  code="lessor.socialIdentities" />:</h2>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="socialIdentities" requestURI="${requestURI}" id="row" uid="socialIdentity">
	
	<jstl:if test="${esMiPerfil}">
			<spring:message code="socialIdentity.edit" var="editHeader" />
			<display:column title="${editHeader}">
				<a href="socialIdentity/edit.do?socialIdentityId=${socialIdentity.id}">
				<spring:message	code="property.edit" />
				</a>
				
			</display:column>
	</jstl:if>
	
	<acme:column sorteable="true" code="auditor.socialIdentity.nick" path="nick"/>
	
	<acme:column sorteable="false" code="auditor.socialIdentity.socialNetwork" path="socialNetwork"/>
	
		<display:column>
			<a href=<jstl:out value="${socialIdentity.link}"/> target="_parent"> 
			<spring:message	code="socialIdentity.link" />
			</a>
		</display:column>
	
</display:table>
<jstl:if test="${esMiPerfil}">
	<a href=socialIdentity/create.do>
	     <spring:message  code="socialIdentity.create" />
	</a>
</jstl:if>

	