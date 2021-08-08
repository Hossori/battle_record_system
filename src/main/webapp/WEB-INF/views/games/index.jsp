<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actGame" value="${ForwardConst.ACT_GAME.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title">ゲーム一覧</c:param>
    <c:param name="content">
        <form name="game" method="POST" action="<c:url value='?action=${actGame}&command=${commCrt}' />">
            <table class="form-table">
                <tr class="game-name">
                    <th><label for="${AttributeConst.GAME_NAME.getValue()}">ゲーム</label></th>
                    <td><input type="text" name="${AttributeConst.GAME_NAME.getValue()}"></td>
                </tr>
                <tr class="mode-name">
                    <th>モード</th>
                    <td>
                        <section id="mode-list">
                            <input type="text" name="${AttributeConst.MODE_NAME.getValue()}"><br />
                        </section>
                        <a href="#" class="navigate" onclick="addMode();">モードを追加</a>
                    </td>
                </tr>
            </table>

            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}">

            <button type="submit">登録</button>
        </form>

        <table class="game-table">
            <tr class="game-header">
               <th class="game_name">ゲーム</th>
               <th class="game_mode_list">モード</th>
               <th class="game_edit">編集</th>
            </tr>
            <c:forEach var="game" items="${games}">
               <tr class="game-data">
                   <td class="game_name"><c:out value="${game.name}" /></td>
                   <td class="game_mode_list">
                       <c:forEach var="mode" items="${game.modeList}">
                           <c:if test="${mode.deleteFlag == AttributeConst.DELETE_FALSE.getIntegerValue()}">
                                <c:out value="${mode.name}" /><br />
                            </c:if>
                       </c:forEach>
                   </td>
                   <td class="game_edit">
                       <a class="navigate" href="<c:out value='?action=${actGame}&command=${commEdit}&game_id=${game.id}' />">編集する</a>
                   </td>
               </tr>
            </c:forEach>
        </table>

        <div class="pagination">
            全 <c:out value="${game_count}" /> 件<br />
            <c:if test="${1 < page}">
                <a href="<c:url value='?action=${actGame}&command=${commIdx}&page=${page-1}' />">前へ</a>
            </c:if>
            <c:forEach var="i" begin="${page_begin}" end="${page_end}" step="1">
                <c:choose>
                    <c:when test="${page == i}">
                        <c:out value="${i}" />
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actGame}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${page < (game_count-1)/maxRow}">
                <a href="<c:url value='?action=${actGame}&command=${commIdx}&page=${page+1}' />">次へ</a>
            </c:if>
        </div>

        <script>
            function addMode() {
                var modeList = document.getElementById("mode-list");
                modeList.insertAdjacentHTML("beforeend", '<input type="text" name="${AttributeConst.MODE_NAME.getValue()}"><br />');
            }
        </script>
    </c:param>
</c:import>