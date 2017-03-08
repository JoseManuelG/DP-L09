
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${!noQuedanMas}">
<form:form action="attributeValue/lessor/edit.do" modelAttribute="attributeValue">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="property" />
		
		<jstl:if test="${attributeValue.id eq 0 }">
		<acme:select items="${attributes}" itemLabel="name" code="attributeValue.attribute" path="attribute"/>
		</jstl:if>	
		<jstl:if test="${attributeValue.id ne 0 }">
		<spring:message  code="attribute.name" />: <jstl:out value="${attributeValue.attribute.name}" />
		<form:hidden path="attribute" />
		</jstl:if>
	
	<acme:textbox code="attributeValue.value" path="value"/>
	<br />
		
	<acme:submit name="save" code="attributeValue.save"/>
	
	<jstl:if test="${attributeValue.id ne 0}">
		<acme:submit name="delete" code="attributeValue.delete"/>
		
	</jstl:if>
	<acme:cancel url="property/view.do?propertyId=${attributeValue.property.id }" code="attributeValue.cancel"/>
	<br>
</form:form>
</jstl:if>
<jstl:if test="${noQuedanMas}">
	<spring:message  code="attributeValue.noHayMas" />
	<br>
	<acme:cancel url="property/view.do?propertyId=${attributeValue.property.id }" code="attributeValue.cancel"/>
</jstl:if>