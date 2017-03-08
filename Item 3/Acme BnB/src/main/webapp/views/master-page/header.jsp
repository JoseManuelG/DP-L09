<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="<spring:url value='/' />">
		<img src="images/logo.png" height="150" alt="Acme BnB Co., Inc." />
	</a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
			<li><a class="fNiv"><spring:message code="master.page.property" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="property/list.do"><spring:message code="master.page.property.list" />
						</a>
					</li>
				</ul>
			</li>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="security/register.do"><spring:message code="master.page.register" /></a></li>
			
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('LESSOR')">
						<li><a href="lessor/myProfile.do"><spring:message code="master.page.lessor.myProfile" /></a></li>
						<li><a href="book/lessor/list.do"><spring:message code="master.page.lessor.request.books" /></a></li>
						<li><a href="property/lessor/myProperties.do"><spring:message code="master.page.lessor.myProperties" /></a></li>
						<li><a href="lessor/fee.do"><spring:message code="master.page.lessor.unpaid.fee" /></a></li>
						<li><a href="creditCard/lessor/myCreditCard.do"><spring:message code="master.page.lessor.creditCard" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('TENANT')">
						<li><a href="tenant/myProfile.do"><spring:message code="master.page.lessor.myProfile" /></a></li>
						<li><a href="finder/tenant/finder.do"><spring:message code="master.page.tenant.finder" /></a></li>
						<li><a href="book/tenant/list.do"><spring:message code="master.page.tenant.request.books" /></a></li>
					</security:authorize>	
					<security:authorize access="hasRole('ADMINISTRATOR')">
						<li><a href="administrator/myProfile.do"><spring:message code="master.page.lessor.myProfile" /></a></li>
						<li><a href="auditor/administrator/registerAuditor.do"><spring:message code="master.page.administrator.registerAuditor" /></a></li>
						<li><a href="attribute/administrator/list.do"><spring:message code="master.page.administrator.attributes" /></a></li>
						<li><a href="configuration/administrator/edit.do"><spring:message code="master.page.administrator.fee" /></a></li>	
						<li><a href="dashboard/administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
						<li><a href="dashboard/administrator/lessors.do"><spring:message code="master.page.administrator.dashboard.lessors" /></a></li>
						<li><a href="dashboard/administrator/attributes.do"><spring:message code="master.page.administrator.dashboard.attributes" /></a></li>
						</security:authorize>	
					<security:authorize access="hasRole('AUDITOR')">
						<li><a href="auditor/myProfile.do"><spring:message code="master.page.lessor.myProfile" /></a></li>
						<li><a href="audit/auditor/auditorlist.do"><spring:message code="master.page.lessor.myAudits" /></a></li>
					</security:authorize>	
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

