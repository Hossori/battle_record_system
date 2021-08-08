<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actUser" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="commMypage" value="${ForwardConst.CMD_MYPAGE.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDst" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title">アカウント編集</c:param>
    <c:param name="content">
        <form method="POST" action="<c:url value='?action=${actUser}&command=${commUpd}' />">
            <table class="form-table">
                <tr class="user-name">
                    <th><label for="${AttributeConst.USER_NAME.getValue()}">ユーザー名</label></th>
                    <td><input type="text" name="${AttributeConst.USER_NAME.getValue()}" value="${user.name}"></td>
                </tr>
                <tr class="user-email">
                    <th><label for="${AttributeConst.USER_EMAIL.getValue()}">メールアドレス</label></th>
                    <td>
                        <input type="email" name="${AttributeConst.USER_EMAIL.getValue()}" placeholder="※変更する場合のみご入力ください"><br />
                        <label class="annotation" for="${AttributeConst.USER_EMAIL.getValue()}"></label>
                    </td>
                </tr>
                <tr class="user-password">
                    <th><label for="${AttributeConst.USER_PASS.getValue()}">パスワード</label>
                    <td>
                        <input type="password" name="${AttributeConst.USER_PASS.getValue()}" placeholder="※変更する場合のみご入力ください"><br />
                        <label class="annotation" for="${AttributeConst.USER_PASS.getValue()}"></label>
                    </td>
                </tr>
                <tr class="user-repassword">
                    <th><label for="${AttributeConst.USER_REPASS.getValue()}">パスワード<br />（再入力）</label>
                    <td>
                        <input type="password" name="${AttributeConst.USER_REPASS.getValue()}" placeholder="※変更する場合のみご入力ください"><br />
                        <label class="annotation" for="${AttributeConst.USER_REPASS.getValue()}"></label>
                    </td>
                </tr>
                <tr class="user-introduction">
                    <th><label for="${AttributeConst.USER_INTRODUCTION.getValue()}">自己紹介</label></th>
                    <td><textarea name="${AttributeConst.USER_INTRODUCTION.getValue()}"><c:out value="${user.introduction}" /></textarea></td>
                </tr>
            </table>

            <input type="hidden" name="${AttributeConst.USER_ID.getValue()}" value="${user.id}">
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}">

            <button type="submit" onclick="document.forms[0].submit();">変更</button>
        </form>

        <br />
        <br />

        <a class="navigate" onclick="confirmDestroy();">アカウントを削除する</a>

        <script>
            function confirmDestroy() {
                if(confirm("本当に削除しますか？")) {
                    location.href="<c:url value='?action=${actUser}&command=${commDst}' />";
                }
            }
        </script>
    </c:param>
</c:import>