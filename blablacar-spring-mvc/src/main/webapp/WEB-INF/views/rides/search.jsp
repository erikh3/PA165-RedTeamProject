<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="blablacar-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%--
  Created by IntelliJ IDEA.
  User: jcibik
  Date: 12/4/17
  Time: 11:17 AM
  To change this template use File | Settings | File Templates.
--%>
<blablacar-tags:page-template title="Search ride">
    <jsp:attribute name="head">
        <!--Load only necessary files-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/ride.css"
              crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/style.css"
              crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/easy-autocomplete.css"
              crossorigin="anonymous">
    </jsp:attribute>
    <jsp:attribute name="body">



            <div class="jumbotron row" style="padding-bottom:0">
                <form:form method="post" action="${pageContext.request.contextPath}/ride/find" id="search-form" modelAttribute="placeForm">
                    <div class="row">
                      <div class="col-xs-6 background text-center">
                        <div id="background1">
                            <div class="green-shader">
                                <h3 class="c-white">From</h3>
                                <form:input path="from" id="autocomplete-from" type="text" form="search-form" />
                            </div>
                        </div>
                      </div>
                      <div class="col-xs-6 background text-center">
                          <div id="background2">
                              <div class="green-shader">
                                  <h3 class="c-white">To:</h3>
                                  <form:input path="to" id="autocomplete-to" type="text" form="search-form" />
                              </div>
                          </div>
                      </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <button class="btn btn-success" type="submit" style="width: 100%; margin-top: 1%">Find</button>
                        </div>
                    </div>
                </form:form>

                <nav class="navbar pull-right" style="margin-top: 1%">
                    <a class="btn btn-default" href="${pageContext.request.contextPath}/ride/list">All rides</a>
                </nav>

            </div>


        <div class="jumbotron">
            <c:if test="${(fn:length(rides) eq 0)}">
                    <h3>
                        No result
                    </h3>
            </c:if>
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
                            <fmt:formatDate value="${ride.departure}" pattern="dd.MM.yyyy" />
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
                            <c:if test="${(not fn:contains(ride.passengers, userSession.user)) && (not (ride.driver.id eq userSession.userId)) && (ride.availableSeats gt 0) }">
                                <form:form action="${pageContext.request.contextPath}/ride/addPassenger" id="join-ride"
                                           method="get">
                                    <button type="submit" class="btn btn-primary" name="rideId" value="${ride.id}">
                                        Join
                                        ride
                                    </button>
                                </form:form>
                            </c:if>
                            <c:if test="${fn:contains(ride.passengers, userSession.user)}">
                                <form:form action="${pageContext.request.contextPath}/ride/removePassenger"
                                           id="join-ride"
                                           method="get">
                                    <button type="submit" class="btn btn-primary" name="rideId" value="${ride.id}">
                                        Leave
                                        ride
                                    </button>
                                </form:form>
                            </c:if>
                        </td>
                        <td>
                            <a class="btn btn-default"
                               href="${pageContext.request.contextPath}/ride/showRide/${ride.id}">Join Disscusion</a>
                        </td>
                    </tr>

                </c:forEach>
            </table>
        </c:if>
        </div>
    </jsp:attribute>


    <jsp:attribute name="foot">
        <!--Load only necessary files-->

        <script>

            $(document).ready(function () {
                $("#background1").addClass("background-" + ($("#autocomplete-from").val()));
                $("#background2").addClass("background-" + ($("#autocomplete-to").val()));
                $("#autocomplete-from").on("change paste keyup input", function(e) {
                    $("#background1").removeClass();
                    $("#background1").addClass("background-" + ($("#autocomplete-from").val()));
                });
                $("#autocomplete-to").on('change paste keyup input',function(e){
                    $("#background2").removeClass();
                    $("#background2").addClass("background-" + ($("#autocomplete-to").val()));
                });
            });

        </script>
        <script src='<c:url value="/resources/javascript/autocomplete/jquery.easy-autocomplete.min.js"/>'></script>
        <script>
            var dataArray = [];
            <c:forEach items="${places}" var="placeF">
            dataArray.push("${placeF.name}");
            </c:forEach>

            var options = {
                data: dataArray,
                list: {
                    match: {
                        enabled: true
                    }
                }
            };

            $("#autocomplete-from").easyAutocomplete(options);
            $("#autocomplete-to").easyAutocomplete(options);
            $(".easy-autocomplete").css("margin", "0 auto");
        </script>
    </jsp:attribute>
</blablacar-tags:page-template>
