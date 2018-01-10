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
<blablacar-tags:page-template title="New comment">
    <jsp:attribute name="head">
        <!--Load only necessary files-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/comment.css"  crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/style.css"
              crossorigin="anonymous">
    </jsp:attribute>
    <jsp:attribute name="body">

        <div class="form-group">
             <form:form action="${pageContext.request.contextPath}/comment/create" id="comment-create-form" method="post" modelAttribute="commentCreateDTO">
                <div class="form-group ${text_error ? 'has-error' : ''}">
                    <form:input class="form-control" path="rideId" type="text" value="${commentCreateDTO.rideId}" cssClass="hidden" />
                    <form:input class="form-control" path="authorId" type="text" value="${commentCreateDTO.authorId}" cssClass="hidden" />
                    <form:errors path="rideId" cssClass="help-block"/>
                    <form:label path="text" cssClass="control-label">Write comment</form:label>
                    <form:errors path="text" cssClass="help-block"/>
                    <form:textarea cssClass="form-control"  form="comment-create-form" path="text" name="text" cols="30" rows="10"></form:textarea>
                    <button class="btn btn-default pull-right" style="margin-top:1%" type="submit">Comment</button>
                </div>
            </form:form>
        </div>


    </jsp:attribute>
    <jsp:attribute name="foot">
        <!--Load only necessary files-->

    </jsp:attribute>
</blablacar-tags:page-template>