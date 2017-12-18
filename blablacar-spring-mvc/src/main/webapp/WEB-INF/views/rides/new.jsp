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
<fmt:message key = "page.welcome.title" var = "title"/>
<blablacar-tags:page-template title="${title}">
    <jsp:attribute name="head">
        <!--Load only necessary files-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/ride.css"  crossorigin="anonymous">
    </jsp:attribute>
    <jsp:attribute name="body">

        <form:form action="${pageContext.request.contextPath}/ride/create" id="ride-create-form" method="post" modelAttribute="rideCreateDTO" class="form-group">
            <form:input path="driverId" type="text" value="${rideCreateDTO.driverId}" cssClass="hidden" />
            <label for="select-from">From</label>
            <form:select id="select-from" path="sourcePlaceId" type="text" form="ride-create-form" class="form-control" >
                <c:forEach items="${places}" var="placeF">
                   <option value="${placeF.id}">
                       ${placeF.name}
                   </option>
                </c:forEach>
            </form:select>
            <label for="select-to">To</label>
            <form:select id="select-to" path="destinationPlaceId" type="text" form="ride-create-form" class="form-control" >
                <c:forEach items="${places}" var="placeT">
                   <option value="${placeT.id}">
                           ${placeT.name}
                   </option>
                </c:forEach>
            </form:select>
            <label for="input-departure">Departure time</label>
            <form:input id="input-departure" path="departure" type="date" form="ride-create-form" class="form-control"/>
            <label for="input-seat-prize">Seat Prize</label>
            <form:input id="input-seat-prize" path="seatPrize" type="number" class="form-control" />
            <label for="input-seats-available">Seats Available</label>
            <form:input id="input-seats-available" path="seatsAvailable" type="number" class="form-control" />

            <button type="submit" class="btn btn-default">Create ride</button>
        </form:form>

        <script>
            $(document).ready(function(){

            });
        </script>



    </jsp:attribute>
    <jsp:attribute name="foot">
        <!--Load only necessary files-->

    </jsp:attribute>
</blablacar-tags:page-template>