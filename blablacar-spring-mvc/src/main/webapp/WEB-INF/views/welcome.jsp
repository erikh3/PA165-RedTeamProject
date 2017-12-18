<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="blablacar-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<%--
  Created by IntelliJ IDEA.
  User: jcibik
  Date: 12/4/17
  Time: 11:17 AM
  To change this template use File | Settings | File Templates.
--%>
<fmt:message key="page.welcome.title" var="title"/>
<blablacar-tags:page-template title="${title}">
    <jsp:attribute name="head">
        <!--Load only necessary files-->
        <meta name="google-signin-client_id"
              content="332736943859-mrr2173fc1kseq1l2i4h0na68mnpmbp3.apps.googleusercontent.com">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/welcome.css"
              crossorigin="anonymous">
    </jsp:attribute>
    <jsp:attribute name="body">
        <%--<fmt:message key="page.welcome.hello-world"/>--%>

        <c:if test="${not userSession.userIsLoggedIn}">
            <div id="sign-in-out-button" class="g-signin2" data-onsuccess="onSignIn"></div>
        </c:if>
        <c:if test="${userSession.userIsLoggedIn}">
            <div>
                <h2>Search</h2>
                <div class="jumbotron">
                    <form:form method="post" action="${pageContext.request.contextPath}/ride/find" id="search-form" modelAttribute="placeForm">
                        <h3>From</h3>
                        <form:select path="fromId" type="text" form="search-form">
                            <c:forEach items="${places}" var="placeF">
                               <form:option value="${placeF.id}">
                                       ${placeF.name}
                               </form:option>
                            </c:forEach>
                        </form:select>
                        <h3>To:</h3>
                        <form:select path="toId" type="text" form="search-form">
                            <c:forEach items="${places}" var="placeF">
                               <form:option value="${placeF.id}">
                                       ${placeF.name}
                               </form:option>
                            </c:forEach>
                        </form:select>
                        <button type="submit">Find</button>
                    </form:form>
                </div>
            </div>

            <c:if test="${not (fn:length(rides) eq 0)}">
                <table class="table rides-list table-hover">
                    <thead>
                    <tr>
                        <td>DRIVER</td>
                        <td>DATE</td>
                        <td>FROM</td>
                        <td>TO</td>
                        <td>SEATS</td>
                        <td>PRIZE</td>
                    </tr>
                    </thead>
                    <c:forEach var="ride" items="${rides}">
                            <tr class="ride-item">
                                <td>
                                    <c:out value="${ride.driver.name}"></c:out>
                                </td>
                                <td>
                                    <c:out value="${ride.departure}"></c:out>
                                </td>
                                <td>
                                    <c:out value="${ride.sourcePlace.name}"></c:out>
                                </td>
                                <td>
                                    <c:out value="${ride.destinationPlace.name}"></c:out>
                                </td>
                                <td>
                                    <c:out value="${ride.availableSeats}"></c:out>
                                </td>
                                <td>
                                    <c:out value="${ride.seatPrice}"></c:out>
                                </td>
                                <td>
                                    <c:if test = "${(not fn:contains(ride.passengers, userSession.user)) && (not (ride.driver.id eq userSession.userId)) && (ride.availableSeats gt 0) }">
                                        <form:form action="${pageContext.request.contextPath}/ride/addPassenger" id="join-ride" method="get">
                                            <button type="submit" class="btn btn-primary" name="rideId" value="${ride.id}">Join ride</button>
                                        </form:form>
                                    </c:if>
                                    <c:if test = "${fn:contains(ride.passengers, userSession.user)}">
                                        <form:form action="${pageContext.request.contextPath}/ride/removePassenger" id="join-ride" method="get">
                                            <button type="submit" class="btn btn-primary" name="rideId" value="${ride.id}">Leave ride</button>
                                        </form:form>
                                    </c:if>
                                </td>
                            </tr>

                    </c:forEach>
                </table>
            </c:if>
        </c:if>

        <script>
        </script>
    </jsp:attribute>
    <jsp:attribute name="foot">
        <!--Load only necessary files-->
        <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
        <script src='<c:url value="/resources/javascript/GoogleOAuth.js"/>'></script>
    </jsp:attribute>
</blablacar-tags:page-template>