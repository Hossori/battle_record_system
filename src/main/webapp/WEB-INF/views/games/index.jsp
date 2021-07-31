<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actGame" value="${ForwardConst.ACT_GAME.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title">ゲーム一覧</c:param>
    <c:param name="content">
        <form name="game" method="POST" action="<c:url value='?action=${actGame}&command=${commCrt}' />">
            <label for="${AttributeConst.GAME_NAME.getValue()}">ゲーム名</label>
            <input type="text" name="${AttributeConst.GAME_NAME.getValue()}">

            <div>モード</div>
            <div id="mode_list">
                <input type="text" name="${AttributeConst.MODE.getValue()}"><br />
            </div>
            <a href="#" id="add_mode">モードを追加</a>

            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}">

            <button type="submit">登録</button>
        </form>

        <table id="game_table">
            <tr>
               <th class="game_name">ゲーム名</th>
               <th class="game_mode_list">モード</th>
               <th class="game_edit">編集</th>
            </tr>
            <c:forEach var="game" items="${games}">
               <tr>
                   <td class="game_name"><c:out value="${game.name}" /></td>
                   <td class="game_mode_list">
                       <c:forEach var="mode" items="${game.modeList}">
                           <c:if test="${mode.deleteFlag == 0}">
                                <c:out value="${mode.name}" /><br />
                            </c:if>
                       </c:forEach>
                   </td>
                   <td class="game_edit">
                       <a href="<c:out value='?action=${actGame}&command=${commEdit}&game_id=${game.id}' />">編集する</a>
                   </td>
               </tr>
            </c:forEach>
        </table>

        <script>
        $(function() {
            $('#add_mode').click(
                function() {
                    $('#mode_list').append('<input type="text" name="${AttributeConst.MODE.getValue()}"><br />');
                }
            );
        });
        </script>
    </c:param>
</c:import>