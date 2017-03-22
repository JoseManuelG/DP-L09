
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

	<spring:message code="RatioOffersVsRequests" />: <jstl:out value="${RatioOffersVsRequests}"/><br/>
	<spring:message code="AvgOfferPerCustomer" />: <jstl:out value="${AvgOfferPerCustomer}"/><br/>
	<spring:message code="AvgApplicationVsCustomerAndRequest" />: <jstl:out value="${AvgApplicationVsCustomerAndRequest}"/><br/>
	
	<spring:message code="CustomerWithMoreApplicationsAccepted" />: <jstl:out value="${CustomerWithMoreApplicationsAccepted.name}"/> <jstl:out value="${CustomerWithMoreApplicationsAccepted.surname}"/><br/>
	<spring:message code="CustomerWithMoreApplicationsDenied" />: <jstl:out value="${CustomerWithMoreApplicationsDenied.name}"/> <jstl:out value="${CustomerWithMoreApplicationsDenied.surname}"/><br/>
	
	<spring:message code="AvgCommentsPerActor" />: <jstl:out value="${AvgCommentsPerActor}"/><br/>	
	<spring:message code="AvgCommentsPerOffer" />: <jstl:out value="${AvgCommentsPerOffer}"/><br/>
	<spring:message code="AvgCommentsPerRequest" />: <jstl:out value="${AvgCommentsPerRequest}"/><br/>
	
	<display:table pagesize="5" class="displaytag1" name="AvgeActorWritingComments"
		requestURI="${requestURI}" id="row" uid="AvgeActorWritingComments">
		
		<spring:message code="actor.name.AvgeActorWritingComments" var="actorName" />
	    <display:column title="${actorName}">
	   	  <jstl:out value="${AvgeActorWritingComments.name}"/>
	   	  <jstl:out value="${AvgeActorWritingComments.surname}"/>
	   	  
	    </display:column>
	</display:table>

	
	
	<spring:message code="AvgMessagesSentPerActor" />: <jstl:out value="${AvgMessagesSentPerActor}"/><br/>	
	<spring:message code="MinMessagesSentPerActor" />: <jstl:out value="${MinMessagesSentPerActor}"/><br/>
	<spring:message code="MaxMessagesSentPerActor" />: <jstl:out value="${MaxMessagesSentPerActor}"/><br/>	
	<spring:message code="AvgMessagesReceivedPerActor" />: <jstl:out value="${AvgMessagesReceivedPerActor}"/><br/>
	<spring:message code="MinMessagesReceivedPerActor" />: <jstl:out value="${MinMessagesReceivedPerActor}"/><br/>
	<spring:message code="MaxMessagesReceivedPerActor" />: <jstl:out value="${MaxMessagesReceivedPerActor}"/><br/>
	<spring:message code="ActorSentMoreMessages" />: <jstl:out value="${ActorSentMoreMessages.name}"/> <jstl:out value="${ActorSentMoreMessages.surname}"/><br/>
	<spring:message code="ActorReceivedMoreMessages" />: <jstl:out value="${ActorReceivedMoreMessages.name}"/> <jstl:out value="${ActorReceivedMoreMessages.surname}"/> <br/>
	
	
	
</fieldset>
