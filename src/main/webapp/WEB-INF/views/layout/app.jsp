<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actAuth" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actUser" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="actGame" value="${ForwardConst.ACT_GAME.getValue()}" />
<c:set var="commLoginForm" value="${ForwardConst.CMD_LOGIN_FORM.getValue()}" />
<c:set var="commLogout" value="${ForwardConst.CMD_LOGOUT.getValue()}" />
<c:set var="commMypage" value="${ForwardConst.CMD_MYPAGE.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEntry" value="${ForwardConst.CMD_ENTRY.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>戦績管理システム</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js" ></script>
        <script type="text/javascript" src="https://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/jquery-datetimepicker@2.5.20/build/jquery.datetimepicker.full.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery-datetimepicker@2.5.20/jquery.datetimepicker.css">
    </head>
    <body>
        <header>
            <div class="header-left">
                <div class="header-logo">
                    <a href="<c:url value='?action=${actTop}&command=${commIdx}' />">戦績管理システム</a>
                </div>
                <div class="header-menu">
                    <ul>
                        <li><a href="<c:url value='?action=${actTop}&command=${commIdx}' />">トップ</a></li>

                        <c:if test="${sessionScope.login_user != null}">
                            <li><a href="<c:url value='?action=${actUser}&command=${commMypage}&user_id=${sessionScope.login_user.id}' />">マイページ</a></li>

                            <c:if test="${sessionScope.login_user.adminFlag == AttributeConst.ADMIN_TRUE.getIntegerValue()}">
                                <li><a href="<c:url value='?action=${actGame}&command=${commIdx}' />">ゲーム管理</a></li>
                            </c:if>
                        </c:if>
                    </ul>
                </div>
            </div>
            <div class="header-right">
                <div class="header-menu">
                    <ul>
                        <c:choose>
                            <c:when test="${sessionScope.login_user == null}">
                                <li><a href="<c:url value='?action=${actAuth}&command=${commLoginForm}' />">ログイン</a></li>
                                <li><a href="<c:url value='?action=${actUser}&command=${commEntry}' />">新規登録</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="<c:url value='?action=${actAuth}&command=${commLogout}' />">ログアウト</a></li>
                                <li><a href="<c:url value='?action=${actUser}&command=${commShow}&user_id=${sessionScope.login_user.id}' />">
                                    <c:out value="${sessionScope.login_user.name}" />
                                </a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
            <div class="clear"></div>
        </header>

        <div id="main">
            <c:if test="${flush != null}">
                <div id="flush"><c:out value='${flush}' /></div>
            </c:if>
            <c:if test="${errors != null}">
                <div id="errors">
                    <c:forEach var="error" items="${errors}">
                       <c:out value="${error}" /><br/>
                    </c:forEach>
                </div>
            </c:if>

            <h2 id="title"><c:out value="${param.title}" /></h2>

            <div id="content">${param.content}</div>
        </div>

        <footer>
            <div class="footer-right">© 2021 Hossori</div>
            <div class="clear"></div>
        </footer>
    </body>
</html>