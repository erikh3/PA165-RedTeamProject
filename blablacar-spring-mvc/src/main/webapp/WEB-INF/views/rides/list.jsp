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
        <ul class="rides-list">

            <script>
                $(document).ready(function(){
                    $("#list_psg_rides").click(function() {
                        $.ajax({
                            url : 'list-pass',
                            success : function (data) {
                                $("#psg_list").html(data);
                            }
                        });

                    });

                });
            </script>

            <button id="list_psg_rides"> Show rides as passenger</button>
            <p id="psg_list"></p>

            <script>
                $(document).ready(function(){
                    $("#list_drv_rides").click(function() {
                        $.ajax({
                            url : 'list-driver',
                            success : function (data) {
                                $("#drv_list").html(data);
                            }
                        });

                    });

                });
            </script>

            <button id="list_drv_rides"> Show rides as driver</button>
            <p id="drv_list"></p>







            <form:form action="${pageContext.request.contextPath}/ride/list" id="list-all-rides" method="post">
                <button type="submit" class="btn btn-primary">All rides</button>
            </form:form>

        </ul>
    </jsp:attribute>
    <jsp:attribute name="foot">
        <!--Load only necessary files-->

    </jsp:attribute>
</blablacar-tags:page-template>