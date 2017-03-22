<%--
 * action-1.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<spring:message code="actor.name" />
:
<jstl:out value="${actor.name}" />
<br>
<spring:message code="actor.surname" />
:
<jstl:out value="${actor.surname}" />
<br>
<spring:message code="actor.email" />
:
<jstl:out value="${actor.email}" />
<br>
<spring:message code="actor.phone" />
:
<jstl:out value="${actor.phone}" />
<br>
<jstl:if test="${!isAdmin and myProfile}">
	<a href="actor/delete.do"><spring:message code="actor.delete"/></a>
</jstl:if>
<h2><spring:message  code="actor.comments" />:</h2>
<jstl:if test="${!unBannedComments.isEmpty()}">
	<display:table pagesize="5" class="displaytag1" name="unBannedComments"
		requestURI="${requestURI}" id="row" uid="unBannedComments">

		<!-- Action links -->
		<jstl:if test="${isAdmin}">
	   	  <display:column title=" ">
	    	  <a href="comment/administrator/ban.do?commentId=${unBannedComments.id}">
	   		  <spring:message code="actor.comment.banComment"/>
	  	 	  </a>
	  	  </display:column>
	    </jstl:if>
		<spring:message code="actor.comment.name" var="actorName" />
	    <display:column title="${actorName}">
	      <a href="actor/view.do?actorId=${unBannedComments.actor.id}">
	   	  <jstl:out value="${unBannedComments.actor.name}"/>
	   	  <jstl:out value="${unBannedComments.actor.surname}"/>
	   	  </a>
	    </display:column>
		<!-- Attributes -->

		<acme:column sorteable="true" code="actor.comment.title" path="title" />

		<acme:column sorteable="true" code="actor.comment.text"
			path="text" />

		<acme:column sorteable="true" code="actor.comment.stars"
			path="stars" />
		<acme:column sorteable="true" code="actor.comment.postMoment"
			path="postMoment" />


	</display:table>
</jstl:if>
	<a href="comment/create.do?commentableId=${actor.id }">
	      <spring:message  code="comment.create" />
	</a>
	

<jstl:if test="${!bannedComments.isEmpty()&& !isAdmin}">
<h2><spring:message  code="actor.bannedComments" />:</h2>
	<display:table pagesize="5" class="displaytag1" name="bannedComments"
		requestURI="${requestURI}" id="row" uid="bannedComments">

		<!-- Action links -->
		<spring:message code="actor.comment.name" var="actorName" />
	    <display:column title="${actorName}">
	      <a href="actor/view.do?actorId=${bannedComments.actor.id}">
	   	  <jstl:out value="${bannedComments.actor.name}"/>
	   	  <jstl:out value="${bannedComments.actor.surname}"/>
	   	  </a>
	    </display:column>
		<!-- Attributes -->
		<acme:column sorteable="true" code="actor.comment.title" path="title" />

		<acme:column sorteable="true" code="actor.comment.text"
			path="text" />

		<acme:column sorteable="true" code="actor.comment.stars"
			path="stars" />
		<acme:column sorteable="true" code="actor.comment.postMoment"
			path="postMoment" />

	</display:table>
</jstl:if>
<jstl:if test="${!allBannedComments.isEmpty()&& isAdmin}">
<h2><spring:message  code="actor.bannedComments" />:</h2>
	<display:table pagesize="5" class="displaytag1" name="allBannedComments"
		requestURI="${requestURI}" id="row" uid="allBannedComments">

		<!-- Action links -->
	   	<display:column title=" ">
	   		<a href="comment/administrator/unban.do?commentId=${allBannedComments.id}">
	   		 <spring:message code="actor.comment.disBanComment"/>
	  	 	</a>
	  	</display:column>
		<spring:message code="actor.comment.name" var="actorName" />
	    <display:column title="${actorName}">
	      <a href="actor/view.do?actorId=${allBannedComments.actor.id}">
	   	  <jstl:out value="${allBannedComments.actor.name}"/>
	   	  <jstl:out value="${allBannedComments.actor.surname}"/>
	   	  </a>
	    </display:column>
		<!-- Attributes -->
		<acme:column sorteable="true" code="actor.comment.title" path="title" />

		<acme:column sorteable="true" code="actor.comment.text"
			path="text" />

		<acme:column sorteable="true" code="actor.comment.stars"
			path="stars" />
	    
		<acme:column sorteable="true" code="actor.comment.postMoment"
			path="postMoment" />
			
	</display:table>
</jstl:if>