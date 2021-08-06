<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actUser" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="commMypage" value="${ForwardConst.CMD_MYPAGE.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDst" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title"></c:param>
    <c:param name="content">
        <form method="POST" action="<c:url value='?action=${actUser}&command=${commUpd}' />">
            <label for="${AttributeConst.USER_NAME.getValue()}">ユーザー名</label>
            <input type="text" name="${AttributeConst.USER_NAME.getValue()}" value="${user.name}">
            <br />

            <label for="${AttributeConst.USER_EMAIL.getValue()}">メールアドレス</label>
            <input type="email" name="${AttributeConst.USER_EMAIL.getValue()}">
            <br />
            <label for="${AttributeConst.USER_EMAIL.getValue()}">※変更する場合のみご入力ください</label>
            <br />

            <label for="${AttributeConst.USER_PASS.getValue()}">パスワード</label>
            <input type="password" name="${AttributeConst.USER_PASS.getValue()}">
            <br />
            <label for="${AttributeConst.USER_PASS.getValue()}">※変更する場合のみご入力ください</label>
            <br />

            <label for="${AttributeConst.USER_INTRODUCTION.getValue()}">自己紹介</label>
            <textarea name="${AttributeConst.USER_INTRODUCTION.getValue()}"><c:out value="${user.introduction}" /></textarea>
            <br />

            <input type="hidden" name="${AttributeConst.USER_ID.getValue()}" value="${user.id}">
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}">

            <button type="submit">変更</button>
        </form>
        <button onclick="confirmDestroy();">アカウントを削除する</button>

        <script>
            function confirmDestroy() {
                if(confirm("本当に削除しますか？")) {
                    location.href="<c:url value='?action=${actUser}&command=${commDst}' />";
                }
            }
        </script>
    </c:param>
</c:import>