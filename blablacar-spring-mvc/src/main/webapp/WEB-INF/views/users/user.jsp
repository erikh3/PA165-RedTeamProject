<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="blablacar-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<%--<fmt:message key = "page.user.title" var = "title"/>--%>


<blablacar-tags:page-template title="${title}">
    <jsp:attribute name="head">
        <!--Load only necessary files-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/user.css" crossorigin="anonymous">
    </jsp:attribute>
    <jsp:attribute name="body">

        <c:set var="contextPath" value="${pageContext.request.contextPath}" />

        <div class="container-fluid main-content">
            <div class="row">
                <div class="col-xs-12 col-md-6">
                    <div class="driver-holder background-holder jumbotron no-padding">
                        <div class="green-shader ">
                            <h2>Driver</h2>
                            <a class="top-button" href="${contextPath}/ride/new" role="button">
                                <div class="">
                                    <p>
                                        Create Ride
                                    </p>
                                </div>
                            </a>
                            <a class="bottom-button" href="${contextPath}/ride/list-driver" role="button">
                                <div class="">
                                    <p>
                                        List Rides
                                    </p>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-md-6">
                    <div class="passenger-holder background-holder jumbotron no-padding">
                        <div class="green-shader">
                            <h2>Passenger</h2>
                            <a class="top-button" href="${contextPath}/ride/list" role="button">
                                <div class="">
                                    <p>
                                        Join Ride
                                    </p>
                                </div>
                            </a>
                            <a class="bottom-button" href="${contextPath}/ride/list-pass" role="button">
                                <div class="">
                                    <p>
                                        List Rides
                                    </p>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="user-last-comments jumbotron">
                <h2>Last Comments</h2>
                <div class="list-group">
                    <c:if test="${fn:length(lastcomments) gt 0}">
                        <c:forEach items="${lastcomments}" var="comment">
                            <c:set var="id" value="${comment}"></c:set>
                            <a href="${contextPath}/ride/showRide/${comment.rideId}" id="${comment.id}" class="list-group-item list-group-item-action">
                                <c:out value="${comment.text}"></c:out>
                                <span class="badge"><fmt:formatDate value="${comment.created}" pattern="dd.MM.yyyy" /></span>
                            </a>
                        </c:forEach>
                    </c:if>
                    <c:if test="${fn:length(lastcomments) eq 0}">
                        <p>No comments</p>
                    </c:if>
                </div>
            </div>
        </div>

    </jsp:attribute>
    <jsp:attribute name="foot">
        <!--Load only necessary files-->

    </jsp:attribute>
</blablacar-tags:page-template>