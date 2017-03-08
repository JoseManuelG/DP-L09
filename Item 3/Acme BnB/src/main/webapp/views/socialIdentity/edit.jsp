
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
<form:form action="socialIdentity/edit.do" modelAttribute="socialIdentity">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="actor" />
			
	

	
	<acme:textbox code="socialIdentity.nick" path="nick"/>

	<acme:textbox code="socialIdentity.socialNetwork" path="socialNetwork"/>

	<acme:textbox code="socialIdentity.link" path="link"/>

	
	<acme:submit name="save" code="socialIdentity.save"/>
	
	<jstl:if test="${socialIdentity.id ne 0}">
		<acme:submit name="delete" code="socialIdentity.delete"/>
	</jstl:if>
	<acme:cancel url='${typeActor}/myProfile.do' code="socialIdentity.cancel"/>
	<br>
</form:form>
