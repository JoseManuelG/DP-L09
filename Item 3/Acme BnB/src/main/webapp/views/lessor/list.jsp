
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

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="lessors" requestURI="${requestURI}" id="row">
		
	<acme:column sorteable="true" code="lessor.name" path="name"/>
	
	<acme:column sorteable="true" code="lessor.surname" path="surname"/>
	
	<display:column>
		<a href="dashboard/administrator/propertiesOrderedByAudits.do?lessorId=${row.id}">
			<spring:message	code="propertiesOrderedByAudits" />
		</a>
	</display:column>
	
	<display:column>
		<a href="dashboard/administrator/propertiesOrderedByBooks.do?lessorId=${row.id}">
			<spring:message	code="propertiesOrderedByBooks" />
		</a>
	</display:column>
	
	<display:column>
		<a href="dashboard/administrator/propertiesOrderedByAcceptedBooks.do?lessorId=${row.id}">
			<spring:message	code="propertiesOrderedByAcceptedBooks" />
		</a>
	</display:column>
	
	<display:column>
		<a href="dashboard/administrator/propertiesOrderedByDeniedBooks.do?lessorId=${row.id}">
			<spring:message	code="propertiesOrderedByDeniedBooks" />
		</a>
	</display:column>
	
	<display:column>
		<a href="dashboard/administrator/propertiesOrderedByPendingBooks.do?lessorId=${row.id}">
			<spring:message	code="propertiesOrderedByPendingBooks" />
		</a>
	</display:column>
	
</display:table>

