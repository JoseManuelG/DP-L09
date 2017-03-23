<%--
 * image.tag
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

<%-- Attributes --%> 
 
<%@ attribute name="url" required="true" %>

<%-- Definition --%>

<img src="<jstl:out value="${url}"/>" height="250" width="250"/>
