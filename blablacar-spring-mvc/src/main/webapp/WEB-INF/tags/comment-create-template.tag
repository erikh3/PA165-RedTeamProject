<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="rideId" required="true" %>
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
<c:set value='${rideId}' var="id" scope="page"/>
<%--<form:form action="${pageContext.request.contextPath}/comment/create" id="comment-create-form" method="post" modelAttribute="commentCreateDTO">--%>
    <%--<div class="form-group ${text_error ? 'has-error' : ''}">--%>
        <%--<form:input path="rideId" type="text" value="${commentCreateDTO.rideId}" cssClass="hidden" />--%>
        <%--<form:input path="authorId" type="text" value="${commentCreateDTO.authorId}" cssClass="hidden" />--%>
        <%--<form:errors path="rideId" cssClass="help-block"/>--%>
        <%--<form:textarea form="comment-create-form" path="text" name="text" cols="30" rows="10">--%>

        <%--</form:textarea>--%>
        <%--<form:errors path="text" cssClass="help-block"/>--%>
        <%--<button type="submit"> Submit new comment to ride with id 1 and user with id 1</button>--%>
    <%--</div>--%>
<%--</form:form>--%>