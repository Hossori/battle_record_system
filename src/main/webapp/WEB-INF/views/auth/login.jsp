<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actAuth" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="commLogin" value="${ForwardConst.CMD_LOGIN.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title">ログイン</c:param>
    <c:param name="content">
        <form method="POST" action="<c:url value='?action=${actAuth}&command=${commLogin}' />">
            <div class="form-div">
                <div class="user-email">
                    <label for="${AttributeConst.USER_EMAIL.getValue()}">メールアドレス</label><br />
                    <input type="email" name="${AttributeConst.USER_EMAIL.getValue()}" value="${user_email}" placeholder="email@address.com">
                </div>
                <div class="user-pass">
                    <label for="${AttributeConst.USER_PASS.getValue()}">パスワード</label><br />
                    <input type="password" name="${AttributeConst.USER_PASS.getValue()}">
                </div>
            </div>

            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}">

            <button type="submit">ログイン</button>
        </form>
    </c:param>
</c:import>