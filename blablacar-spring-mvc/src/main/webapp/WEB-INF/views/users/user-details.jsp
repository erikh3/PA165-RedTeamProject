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
<blablacar-tags:page-template title="User details">
    <jsp:attribute name="head">
        <!--Load only necessary files-->
        <meta name="google-signin-client_id"
              content="332736943859-mrr2173fc1kseq1l2i4h0na68mnpmbp3.apps.googleusercontent.com">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/welcome.css"
              crossorigin="anonymous">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/style.css"
                      crossorigin="anonymous">
    </jsp:attribute>
    <jsp:attribute name="body">
        <div class="jumbotron">
            <div class="row">

                <div class="col-xs-2 text-center">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <h3>ID:</h3>
                        </li>
                        <li class="list-group-item">
                            <h3>Name:</h3>
                        </li>
                        <li class="list-group-item">
                            <h3>Surname:</h3>
                        </li>
                        <li class="list-group-item">
                            <h3>Mail:</h3>
                        </li>
                    </ul>
                </div>
                <div class="col-xs-10">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <h3>${user.id}</h3>
                        </li>
                        <li class="list-group-item">
                            <h3>${user.name}</h3>
                        </li>
                        <li class="list-group-item">
                            <h3>${user.surname}</h3>
                        </li>
                        <li class="list-group-item">
                            <h3>${user.nickname}</h3>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </jsp:attribute>
    <jsp:attribute name="foot">
        <!--Load only necessary files-->
    </jsp:attribute>
</blablacar-tags:page-template>