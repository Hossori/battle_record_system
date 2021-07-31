<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actGame" value="${ForwardConst.ACT_GAME.getValue()}" />
<c:set var="actMode" value="${ForwardConst.ACT_MODE.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDst" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title">ゲーム編集</c:param>
    <c:param name="content">
        <!-- ゲーム名の変更 -->
        <form name="update_game" method="POST" action="<c:url value='?action=${actGame}&command=${commUpd}' />">
            <label for="${AttributeConst.GAME_NAME.getValue()}">ゲーム名</label>
            <input type="text" name="${AttributeConst.GAME_NAME.getValue()}" value="${game.name}">

            <input type="hidden" name="${AttributeConst.GAME_ID.getValue()}" value="${game.id}">
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}">

            <button type="submit">変更</button>
        </form>

        <!-- ゲームの削除 -->
        <a href="#" onclick="(function(){var name='destroy_game';confirmDestroy(name);})();">ゲームの削除</a>
        <form name="destroy_game" method="POST" action="<c:url value='?action=${actGame}&command=${commDst}' />">
            <input type="hidden" name="${AttributeConst.GAME_ID.getValue()}" value="${game.id}">
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}">
        </form>

        <!-- モードの削除 -->
        モード<br />
        <c:forEach var="mode" items="${game.modeList}" varStatus="status">
            <c:if test="${mode.deleteFlag == 0}">
                <c:out value="${mode.name}" />
                <a href="#" onclick="(function(){var name='destroy_mode${status.count}';confirmDestroy(name);})();">削除</a>
                <form name="destroy_mode${status.count}" method="POST" action="<c:url value='?action=${actMode}&command=${commDst}' />">
                    <input type="hidden" name="${AttributeConst.GAME_ID.getValue()}" value="${game.id}">
                    <input type="hidden" name="${AttributeConst.MODE_ID.getValue()}" value="${mode.id}">
                    <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}">
                </form>
            </c:if>
        </c:forEach>

        <!-- モードの追加 -->
        <form name="create_mode" method="POST" action="<c:url value='?action=${actMode}&command=${commCrt}' />">
            <input type="text" name="${AttributeConst.MODE_NAME.getValue()}">

            <input type="hidden" name="${AttributeConst.GAME_ID.getValue()}" value="${game.id}">
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}">

            <button type="submit">追加</button>
        </form>

        <a href="<c:url value='?action=${actGame}&command=${commIdx}' />">一覧に戻る</a>

        <script>
        function confirmDestroy(name) {
            if(confirm("削除してよろしいですか？")){
                document.forms[name].submit();
            }
        }
        </script>
    </c:param>
</c:import>