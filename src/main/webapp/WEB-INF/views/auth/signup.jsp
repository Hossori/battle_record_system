<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actAuth" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="commSignup" value="${ForwardConst.CMD_SIGNUP.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title">新規登録</c:param>
    <c:param name="content">
        <form method="POST" action="<c:url value='?action=${actAuth}&command=${commSignup}' />">
            <label for="${AttributeConst.USER_NAME.getValue()}">ユーザー名</label><br />
            <input type="text" name="${AttributeConst.USER_NAME.getValue()}"><br />

            <label for="${AttributeConst.USER_EMAIL.getValue()}">メールアドレス</label><br />
            <input type="email" name="${AttributeConst.USER_EMAIL.getValue()}" placeholder="email@address.com"><br />

            <label for="${AttributeConst.USER_PASS.getValue()}">パスワード</label><br />
            <input type="password" name="${AttributeConst.USER_PASS.getValue()}"><br />

            <input type="hidden" value="${AttributeConst.TOKEN.getValue()}">
            <button type="submit">登録</button>
        </form>
    </c:param>
</c:import>