<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="foot" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="blablacar-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: jcibik
  Date: 12/8/17
  Time: 11:29 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${title}"/></title>
    <!-- bootstrap loaded from content delivery network -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css"  crossorigin="anonymous">
    <jsp:invoke fragment="head"/>
</head>
<body style="background-color: #e8fced!important;">
<!-- navigation bar -->
<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}">PA165 BlaBlaCar</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav" style="float: none;">
                <c:if test="${not empty userSession.userId}">
                    <li>
                        <blablacar-tags:a href="/user">Home</blablacar-tags:a>
                    </li>
                    <c:if test="${userSession.isAdmin}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Admin<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><blablacar-tags:a href="/comment/manage">Admin comments</blablacar-tags:a></li>
                        </ul>
                    </li>
                    </c:if>
                    <li class="pull-right">
                        <blablacar-tags:a href="/user/user-details">Logged in as: ${userSession.user.name}</blablacar-tags:a>
                    </li>
                </c:if>

                <c:if test="${userSession.userIsLoggedIn}">
                    <li class="pull-right">
                        <a  href="#" id="sign-out-btn" >Sign out</a>
                    </li>
                </c:if>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="container">

    <!-- page title -->
    <c:if test="${not empty title}">
        <div class="page-header">
            <h1><c:out value="${title}"/></h1>
        </div>
    </c:if>

    <!-- authenticated user info -->
    <c:if test="${not empty authenticatedUser}">
        <div class="row">
            <div class="col-xs-6 col-sm-8 col-md-9 col-lg-10"></div>
            <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <c:out value="${authenticatedUser.givenName} ${authenticatedUser.surname}"/>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <!-- alerts -->
    <c:if test="${not empty alert_danger}">
        <div class="alert alert-danger" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            <c:out value="${alert_danger}"/></div>
    </c:if>
    <c:if test="${not empty alert_info}">
        <div class="alert alert-info" role="alert"><c:out value="${alert_info}"/></div>
    </c:if>
    <c:if test="${not empty alert_success}">
        <div class="alert alert-success" role="alert"><c:out value="${alert_success}"/></div>
    </c:if>
    <c:if test="${not empty alert_warning}">
        <div class="alert alert-warning" role="alert"><c:out value="${alert_warning}"/></div>
    </c:if>

    <!-- page body -->
    <jsp:invoke fragment="body"/>

    <div style="width:100%; height:1px; padding-bottom: 30px;"></div>

    <!-- footer -->
    <footer class="footer" style="position: fixed; right: 0; bottom: 0; left: 0; padding: 1rem; background-color: #333; color: #9d9d9d;">
        <p style="margin:0">&copy;&nbsp;<%=java.time.Year.now().toString()%>&nbsp;Masaryk University</p>
    </footer>
</div>
<!-- javascripts placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src='<c:url value="/resources/javascript/application.js" />'></script>
<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
<script src='<c:url value="/resources/javascript/GoogleOAuth.js"/>'></script>

<jsp:invoke fragment="foot"/>
</body>
</html>

