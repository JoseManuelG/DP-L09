
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
<form:form action="book/tenant/book.do" modelAttribute="bookForm">

	<form:hidden path="propertyId" />
	
	<acme:textbox code="book.checkin" path="checkInDate" placeholder="dd/mm/aaaa"/>
	<acme:textbox code="book.checkout" path="checkOutDate" placeholder="dd/mm/aaaa"/>
	<acme:checkbox code="book.smoker" path="smoker"/>
	<fieldset>
	<legend><spring:message code="book.credit.card"/></legend>
	<acme:textbox code="credit.card.brand.name" path="creditCard.brandName"/>
	<acme:textbox code="credit.card.cvv.code" path="creditCard.cvvCode"/>
	<acme:textbox code="credit.card.expiration.month" path="creditCard.expirationMonth"/>
	<acme:textbox code="credit.card.expiration.year" path="creditCard.expirationYear"/>
	<acme:textbox code="credit.card.holder.name" path="creditCard.holderName"/>
	<acme:textbox code="credit.card.number" path="creditCard.number"/>
	</fieldset>
	
	<acme:submit name="book" code="book"/>
	<acme:cancel url="finder/tenant/finder.do" code="book.cancel"/>
</form:form>
