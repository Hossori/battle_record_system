<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actUser" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="commMypage" value="${ForwardConst.CMD_MYPAGE.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title"><c:out value="${user.name}" /></c:param>
    <c:param name="content">
        <div class="introduction">
            自己紹介<br />
            <pre><c:out value="${user.introduction}" /></pre>
        </div>
        <a href="<c:url value='?action=${actUser}&command=${commMypage}&user_id=${user.id}' />"><c:out value="${user.name}" />の戦績</a>
        <c:if test="${user.id == sessionScope.login_user.id}">
            <a href="<c:url value='?action=${actUser}&command=${commEdit}' />">アカウント情報の編集</a>
        </c:if>
    </c:param>
</c:import>