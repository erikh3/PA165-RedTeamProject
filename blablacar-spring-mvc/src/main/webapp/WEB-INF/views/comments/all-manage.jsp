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
<blablacar-tags:page-template title="Manage comments">
    <jsp:attribute name="head">
        <!--Load only necessary files-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/comment.css"  crossorigin="anonymous">
    </jsp:attribute>
    <jsp:attribute name="body">

        <c:if test="${fn:length(comments) eq 0}">
                        <p>No comments</p>
        </c:if>
        <c:if test="${not (fn:length(comments) eq 0)}">
            <table class="table rides-list table-hover">
                <thead>
                <tr>
                    <td>RIDE</td>
                    <td>DATE</td>
                    <td>AUTHOR</td>
                    <td>TEXT</td>
                </tr>
                </thead>
                <c:forEach var="comment" items="${comments}">
                    <tr class="comment-item">
                        <td>
                            <c:out value="${comment.rideId}"></c:out>
                        </td>
                        <td>
                            <c:out value="${comment.created}"></c:out>
                        </td>
                        <td>
                            <c:out value="${comment.author.nickname}"></c:out>
                        </td>
                        <td>
                            <c:out value="${comment.text}"></c:out>
                        </td>
                        <td>
                            <form:form action="${pageContext.request.contextPath}/comment/delete" id="delete-comment" method="get">
                                <button type="submit" class="btn btn-warning" name="commentId" value="${comment.id}">Delete comment</button>
                            </form:form>
                        </td>
                    </tr>
            </c:forEach>
            </table>
        </c:if>
    </jsp:attribute>
    <jsp:attribute name="foot">
        <!--Load only necessary files-->

    </jsp:attribute>
</blablacar-tags:page-template>