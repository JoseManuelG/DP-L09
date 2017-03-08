
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="audits" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->

	
		<spring:message code="audit.edit.audit" var="editHeader" />
		<display:column title="${editHeader}">
			<jstl:if test="${row.draftMode }">
				<a href="audit/auditor/edit.do?auditId=${row.id}">
					<spring:message	code="audit.edit" />
				</a>				
			</jstl:if>
		</display:column>
		
		<spring:message code="audit.view.audit" var="viewHeader" />
			<display:column title="${viewHeader}">
			<a href="audit/view.do?auditId=${row.id}">
				<spring:message	code="audit.view" />
			</a>
		</display:column>
	
	<!-- Attributes -->
	
	<spring:message code="audit.text" var="text" />
	<display:column property="text" title="${text}" sortable="false" />
	<spring:message code="audit.draftMode" var="draftMode" />
	<display:column property="draftMode" title="${draftMode}" sortable="true" />

</display:table>


