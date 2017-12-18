<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="blablacar-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: jcibik
  Date: 12/8/17
  Time: 12:52 PM
  To change this template use File | Settings | File Templates.
--%>
<fmt:message key = "page.welcome.title" var = "title"/>
<blablacar-tags:page-template title="${title}">
    <jsp:attribute name="head">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/welcome.css"  crossorigin="anonymous">
    </jsp:attribute>
    <jsp:attribute name="body">

        <a href="#" onclick="signOut();">Sign out</a>

    </jsp:attribute>
    <jsp:attribute name="foot">
        <!--Load only necessary files-->
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <script src='<c:url value="/resources/javascript/GoogleOAuth.js"/>'></script>
    </jsp:attribute>
</blablacar-tags:page-template>
