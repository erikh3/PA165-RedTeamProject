<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="blablacar-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: jcibik
  Date: 12/4/17
  Time: 11:17 AM
  To change this template use File | Settings | File Templates.
--%>
<blablacar-tags:page-template title="Create New Ride">
    <jsp:attribute name="head">
        <!--Load only necessary files-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/ride.css"
              crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/style.css"
              crossorigin="anonymous">
    </jsp:attribute>
    <jsp:attribute name="body">

        <div class="row">

            <form:form action="${pageContext.request.contextPath}/ride/create" id="ride-create-form" method="post"
                       modelAttribute="rideCreateDTO" cssClass="form-horizontal">

                <div class="form-group ${name_error?'has-error':''}">
                    <form:input path="driverId" type="text" value="${rideCreateDTO.driverId}" cssClass="hidden"/>

                    <form:label path="sourcePlaceId" cssClass="col-sm-2 control-label">From</form:label>
                    <div class="col-sm-10">
                        <form:select id="select-from" path="sourcePlaceId" type="text" form="ride-create-form"
                                     cssClass="form-control">
                            <c:forEach items="${places}" var="placeF">
                               <option value="${placeF.id}">
                                       ${placeF.name}
                               </option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="sourcePlaceId" cssClass="help-block"/>
                    </div>

                    <form:label path="destinationPlaceId" cssClass="col-sm-2 control-label">To</form:label>
                    <div class="col-sm-10">
                        <form:select id="select-to" path="destinationPlaceId" type="text" form="ride-create-form"
                                     cssClass="form-control">
                            <c:forEach items="${places}" var="placeT">
                               <option value="${placeT.id}">
                                       ${placeT.name}
                               </option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="destinationPlaceId" cssClass="help-block"/>
                    </div>

                    <form:label path="departure" cssClass="col-sm-2 control-label">Departure time</form:label>
                    <div class="col-sm-10">
                        <form:input id="input-departure" path="departure" type="date" form="ride-create-form"
                                    cssClass="form-control"/>
                        <form:errors path="departure" cssClass="help-block"/>
                    </div>

                    <form:label path="seatPrize" cssClass="col-sm-2 control-label">Seat Prize</form:label>
                    <div class="col-sm-10">
                        <form:input id="input-seat-prize" path="seatPrize" type="number" cssClass="form-control"/>
                        <form:errors path="seatPrize" cssClass="help-block"/>
                    </div>

                    <form:label path="seatsAvailable" cssClass="col-sm-2 control-label">Seats Available</form:label>
                    <div class="col-sm-10">
                        <form:input id="input-seats-available" path="seatsAvailable" type="number" cssClass="form-control"/>
                        <form:errors path="seatsAvailable" cssClass="help-block"/>
                    </div>

                    <button type="submit" class="btn btn-default pull-right" style="margin-top:1%; margin-right:15px;">Create ride</button>
                </div>
            </form:form>
        </div>

    </jsp:attribute>
    <jsp:attribute name="foot">
        <!--Load only necessary files-->

    </jsp:attribute>
</blablacar-tags:page-template>