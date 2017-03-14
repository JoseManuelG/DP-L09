
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<jstl:if test="${lessor!=null}">
	<p>
		<spring:message code="property.dashboard.prefix"/> 
		<jstl:out value="${lessor.name}"/> <jstl:out value="${lessor.surname}"/> 
		<jstl:choose>
			<jstl:when test="${queryNumber==1}"><spring:message code="property.dashboard.sufix1"/></jstl:when>
			<jstl:when test="${queryNumber==2}"><spring:message code="property.dashboard.sufix2"/></jstl:when>
			<jstl:when test="${queryNumber==3}"><spring:message code="property.dashboard.sufix3"/></jstl:when>
			<jstl:when test="${queryNumber==4}"><spring:message code="property.dashboard.sufix4"/></jstl:when>
			<jstl:when test="${queryNumber==5}"><spring:message code="property.dashboard.sufix5"/></jstl:when>
			<jstl:otherwise/>
		</jstl:choose>
	</p>
</jstl:if>


<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="properties" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	<security:authorize access="hasRole('LESSOR')">
	<jstl:if test="${requestURI == 'property/lessor/myProperties.do'}">
			<spring:message code="property.edit.property" var="editHeader" />
			<display:column title="${editHeader}">
				<a href="property/lessor/edit.do?propertyId=${row.id}">
				<spring:message	code="property.edit" />
				</a>
				
			</display:column>
	</jstl:if>
	</security:authorize>
	<spring:message code="property.view.title" var="viewTitleHeader" />
	<display:column title="${viewTitleHeader}">
			<a href="property/view.do?propertyId=${row.id}">
				<spring:message	code="property.view" />
			</a>
	</display:column>
	<jstl:if test="${requestURI == 'property/list.do'}">
	    <spring:message code="property.view.lessor" var="viewTitleHeader" />
	    <display:column title="${viewTitleHeader}">
	      <a href="lessor/view.do?lessorId=${row.lessor.id}">
	      <spring:message  code="property.view" />
	    </a>
	      
	    </display:column>
  	</jstl:if>

	<!-- Attributes -->
	
	<spring:message code="property.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false" />
	
	<spring:message code="property.rate" var="rateHeader" />
	<display:column property="rate" title="${rateHeader}" sortable="false" />
	
	<spring:message code="property.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />

	<spring:message code="property.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}" sortable="false" />
	
	<jstl:if test="${lessor==null}">
		<spring:message code="property.number" var="numberHeader" />
		<display:column title="${numberHeader}" sortable="false">
			<jstl:out value="${row.books.size()}"/>
		</display:column>
	</jstl:if>
	
	
</display:table>

<jstl:if test="${lessor!=null}">
	<p>
		<acme:cancel url="dashboard/administrator/lessors.do" code="property.back"/>
	</p>
</jstl:if>

<security:authorize access="hasRole('LESSOR')">
	<a href="property/lessor/create.do">
	      <spring:message  code="property.new" />
	</a>
</security:authorize>

