<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="date" class="java.util.Date" />

<hr />

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme BnB Co., Inc.</b> | 
<a href="<spring:url value='/about/about.do'/>"><spring:message code="footer.about" /></a> | 
<a href="<spring:url value='/law/terms-conditions.do'/>"><spring:message code="footer.terms" /></a>

<jstl:if test="${!cookie['cookiesAccepted'].value}">
		<div id="cookies-message">
			<spring:message code="master.page.cookies.header" /><br/>
			<spring:message code="master.page.cookies.body1"/>
			<a href="law/terms-conditions.do#cookies-policy"><spring:message code="master.page.cookies.bodylink"/></a><spring:message code="master.page.cookies.body2"/>&nbsp;
			<p class="link" onclick="acceptCookies()"><spring:message code="master.page.cookies.accept" /></p>
		</div>
</jstl:if>