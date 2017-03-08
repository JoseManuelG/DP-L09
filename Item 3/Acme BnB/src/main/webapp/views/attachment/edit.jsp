
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


<form:form action="attachment/auditor/edit.do" modelAttribute="attachment">
	
	<form:hidden path="id" />
	<form:hidden path="audit" />
	
	<acme:textbox code="attachment.name" path="name"/>
	
	<acme:textbox code="attachment.url" path="url" />

	<acme:submit name="save" code="attachment.save"/>
	
	<jstl:if test="${attachment.id != 0}">
		<acme:submit name="delete" code="attachment.delete"/>
	</jstl:if>
	
	<acme:cancel url="audit/view.do?auditId=${attachment.audit.id}" code="attachment.cancel"/>
</form:form>
