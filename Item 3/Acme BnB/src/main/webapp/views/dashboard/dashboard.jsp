
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

<fieldset>
	<spring:message code="AverageAcceptedBooksPerLessor" />: <jstl:out value="${AverageAcceptedBooksPerLessor}"/><br/>
	<spring:message code="AverageDeniedBooksPerLessor" />: <jstl:out value="${AverageDeniedBooksPerLessor}"/><br/>
	<spring:message code="AverageAcceptedBooksPerTenant" />: <jstl:out value="${AverageAcceptedBooksPerTenant}"/><br/>
	<spring:message code="AverageDeniedBooksPerTenant" />: <jstl:out value="${AverageDeniedBooksPerTenant}"/><br/>
	<spring:message code="LessorWithMoreAcceptedBooks" />: <jstl:out value="${LessorWithMoreAcceptedBooks.name}"/> <jstl:out value="${LessorWithMoreAcceptedBooks.surname}"/><br/>
	<spring:message code="LessorWithMoreDeniedBooks" />: <jstl:out value="${LessorWithMoreDeniedBooks.name}"/> <jstl:out value="${LessorWithMoreDeniedBooks.surname}"/><br/>
	<spring:message code="LessorWithMorePendingBooks" />: <jstl:out value="${LessorWithMorePendingBooks.name}"/> <jstl:out value="${LessorWithMorePendingBooks.surname}"/><br/>
	<spring:message code="TenantWithMoreAcceptedBooks" />: <jstl:out value="${TenantWithMoreAcceptedBooks.name}"/> <jstl:out value="${TenantWithMoreAcceptedBooks.surname}"/><br/>
	<spring:message code="TenantWithMoreDeniedBooks" />: <jstl:out value="${TenantWithMoreDeniedBooks.name}"/> <jstl:out value="${TenantWithMoreDeniedBooks.surname}"/><br/>
	<spring:message code="TenantWithMorePendingBooks" />: <jstl:out value="${TenantWithMorePendingBooks.name}"/> <jstl:out value="${TenantWithMorePendingBooks.surname}"/><br/>
	<spring:message code="LessorWithMaxAcceptedVersusTotalBooksRatio" />: <jstl:out value="${LessorWithMaxAcceptedVersusTotalBooksRatio.name}"/> <jstl:out value="${LessorWithMaxAcceptedVersusTotalBooksRatio.surname}"/><br/>
	<spring:message code="LessorWithMinAcceptedVersusTotalBooksRatio" />: <jstl:out value="${LessorWithMinAcceptedVersusTotalBooksRatio.name}"/> <jstl:out value="${LessorWithMinAcceptedVersusTotalBooksRatio.surname}"/><br/>
	<spring:message code="TenantWithMaxAcceptedVersusTotalBooksRatio" />: <jstl:out value="${TenantWithMaxAcceptedVersusTotalBooksRatio.name}"/> <jstl:out value="${TenantWithMaxAcceptedVersusTotalBooksRatio.surname}"/><br/>
	<spring:message code="TenantWithMinAcceptedVersusTotalBooksRatio" />: <jstl:out value="${TenantWithMinAcceptedVersusTotalBooksRatio.name}"/> <jstl:out value="${TenantWithMinAcceptedVersusTotalBooksRatio.surname}"/><br/>
	<spring:message code="AverageResultsPerFinder" />: <jstl:out value="${AverageResultsPerFinder}"/><br/>
	<spring:message code="MinimumResultsPerFinder" />: <jstl:out value="${MinimumResultsPerFinder}"/><br/>
	<spring:message code="MaximumResultsPerFinder" />: <jstl:out value="${MaximumResultsPerFinder}"/><br/>
	<spring:message code="MinimumSocialIdentitiesPerActor" />: <jstl:out value="${MinimumSocialIdentitiesPerActor}"/><br/>
	<spring:message code="AverageSocialIdentitiesPerActor" />: <jstl:out value="${AverageSocialIdentitiesPerActor}"/><br/>
	<spring:message code="MaximumSocialIdentitiesPerActor" />: <jstl:out value="${MaximumSocialIdentitiesPerActor}"/><br/>
	<spring:message code="MinimumInvoicesPerTenant" />: <jstl:out value="${MinimumInvoicesPerTenant}"/><br/>
	<spring:message code="AverageInvoicesPerTenant" />: <jstl:out value="${AverageInvoicesPerTenant}"/><br/>
	<spring:message code="MaximumInvoicesPerTenant" />: <jstl:out value="${MaximumInvoicesPerTenant}"/><br/>
	<spring:message code="TotalDueMoneyOfInvoices" />: <jstl:out value="${TotalDueMoneyOfInvoices}"/><br/>
	<spring:message code="AverageRequestsWithAuditsVersusNoAudits" />: <jstl:out value="${AverageRequestsWithAuditsVersusNoAudits}"/><br/>
	<spring:message code="MinimumAuditsPerProperty" />: <jstl:out value="${MinimumAuditsPerProperty}"/><br/>
	<spring:message code="AverageAuditsPerProperty" />: <jstl:out value="${AverageAuditsPerProperty}"/><br/>
	<spring:message code="MaximumAuditsPerProperty" />: <jstl:out value="${MaximumAuditsPerProperty}"/><br/>
</fieldset>
