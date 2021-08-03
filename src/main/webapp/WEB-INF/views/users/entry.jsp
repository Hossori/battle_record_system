<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actUser" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title">新規登録</c:param>
    <c:param name="content">
        <form method="POST" action="<c:url value='?action=${actUser}&command=${commCrt}' />">
            <label for="${AttributeConst.USER_EMAIL.getValue()}">メールアドレス</label><br />
            <input type="email" name="${AttributeConst.USER_EMAIL.getValue()}"
                    value="${user_email}" placeholder="email@address.com"><br />

            <label for="${AttributeConst.USER_NAME.getValue()}">ユーザー名</label><br />
            <input type="text" name="${AttributeConst.USER_NAME.getValue()}" value="${user_name}"><br />

            <label for="${AttributeConst.USER_PASS.getValue()}">パスワード</label><br />
            <input type="password" name="${AttributeConst.USER_PASS.getValue()}"><br />

            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}">

            <button type="submit">登録</button>
        </form>
    </c:param>
</c:import>