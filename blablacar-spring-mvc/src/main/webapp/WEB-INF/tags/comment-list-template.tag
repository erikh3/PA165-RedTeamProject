<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="comments" required="true" %>
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

<c:set value='${comments}' var="comments" scope="page"/>
<ul class="list-group">
    <c:forEach items="${comments}" var="comment">
        <li class="list-group-item">
            <c:out value="${comment}" />
        </li>
    </c:forEach>
</ul>
