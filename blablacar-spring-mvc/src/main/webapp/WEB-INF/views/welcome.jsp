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
<blablacar-tags:page-template title="Welcome">
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
            <h1>BlaBlaCar</h1>
            <br/>
            <p>To continue to the app, please sign in using Gmail:</p>

            <div id="sign-in-out-button" class="g-signin2" data-onsuccess="onSignIn"></div>
        </div>

        <script>
        </script>
    </jsp:attribute>
    <jsp:attribute name="foot">
        <!--Load only necessary files-->
    </jsp:attribute>
</blablacar-tags:page-template>