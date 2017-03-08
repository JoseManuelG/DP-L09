
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

<form:form action="property/lessor/edit.do" modelAttribute="property">
	
	<form:hidden path="id" />
	
	<acme:textbox code="property.name" path="name"/>
	<acme:textbox code="property.rate" path="rate"/>
	<acme:textbox code="property.description" path="description"/>
	<acme:textbox code="property.address" path="address"/>

	
	
	<acme:submit name="save" code="property.save"/>
	
	<jstl:if test="${property.id ne 0}">
		<acme:submit name="delete" code="property.delete"/>
	</jstl:if>
	<acme:cancel url="property/lessor/myProperties.do" code="property.cancel"/>
	<br>
</form:form>