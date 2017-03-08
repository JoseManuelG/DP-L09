
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



<spring:message code="invoice.creationMoment"/>:
<jstl:out value="${invoice.creationMoment}"/><br/>

<spring:message code="invoice.VAT"/>:
<jstl:out value="${invoice.VAT}"/><br/>

<fieldset>
	<legend><spring:message code="invoice.tenantInformation"/></legend>
	<jstl:out value="${invoice.information}"/>
</fieldset>
<fieldset>
	<legend><spring:message code="invoice.bookDetails"/></legend>
	<jstl:out value="${invoice.details}"/>
</fieldset>