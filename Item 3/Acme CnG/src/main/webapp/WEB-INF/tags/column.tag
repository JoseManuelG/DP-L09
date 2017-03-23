<%--
 * textbox.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Attributes --%> 
 
<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="sorteable" required="true" %>

<%@ attribute name="highlight" required="false" %>

<jstl:if test="${highlight == null}">
	<jstl:set var="highlight" value="false" />
</jstl:if>

<jstl:if test="${highlight}">
	<jstl:set var="style" value="color: red;text-decoration:line-through " />
</jstl:if>

<jstl:if test="${!highlight}">
	<jstl:set var="style" value="" />
</jstl:if>
<%-- Definition --%>
	
<spring:message code="${code}" var="codeName" />
<display:column property="${path}" title="${codeName}" sortable="${sorteable}" style="${style}"/>
