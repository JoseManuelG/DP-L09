
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


<p><spring:message code="audit.howto.attachments" /></p>

<form:form action="audit/auditor/edit.do" modelAttribute="audit">
	
		<form:hidden path="id" />
		<form:hidden path="property" />
		
	<acme:textbox code="audit.text" path="text"/>
	<br />
		
	<button type="submit" name="save" class="btn btn-primary"
			onclick="return confirm('<spring:message code="audit.confirm.save" />')">
				<spring:message code="audit.save" />
	</button>
	
	<acme:submit name="draftsave" code="audit.draftsave"/>
	
	<jstl:if test="${audit.id ne 0}">
		<acme:submit name="delete" code="audit.delete"/>
	</jstl:if>
	
	<acme:cancel url="audit/auditor/auditorlist.do" code="audit.cancel"/>
	
</form:form>
