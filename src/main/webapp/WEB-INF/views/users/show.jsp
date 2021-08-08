<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actUser" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="commMypage" value="${ForwardConst.CMD_MYPAGE.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title"><c:out value="${user.name}" /></c:param>
    <c:param name="content">
        <div class="user-show">
            <div class="record-count">
                <nav>戦績数</nav>
                <c:out value="${record_count}" />
            </div>

            <div class="introduction">
                <nav>説明</nav><br />
                <pre><c:out value="${user.introduction}" /></pre>
            </div>

            <a class="navigate" href="<c:url value='?action=${actUser}&command=${commMypage}&user_id=${user.id}' />"><c:out value="${user.name}" />の戦績</a>
            <br />

            <c:if test="${user.id == sessionScope.login_user.id}">
                <a class="navigate" href="<c:url value='?action=${actUser}&command=${commEdit}' />">アカウント情報の編集</a>
            </c:if>
        </div>
    </c:param>
</c:import>