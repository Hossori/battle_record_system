<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actRecord" value="${ForwardConst.ACT_RECORD.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title">みんなの戦績</c:param>
    <c:param name="content">
        <form method="POST" action="<c:url value='?action=${actTop}&command=${commIdx}' />">
            <label for="${AttributeConst.GAME_NAME.getValue()}">ゲーム</label><br />
            <select id="game_list" name="${AttributeConst.GAME_ID.getValue()}">
                <option value=0 label="全て" />
                <c:forEach var="game" items="${games}">
                    <option value="${game.id}" label="${game.name}" <c:if test='${game.id == game_id_selected}'>selected</c:if> />
                </c:forEach>
            </select>
            <br />

            <label for="${AttributeConst.MODE_NAME.getValue()}">モード</label><br />
            <select id="mode_list" name="${AttributeConst.MODE_ID.getValue()}">
                <option value=0 label="全て" />
                <c:forEach var="mode" items="${modes}">
                    <option value="${mode.id}" label="${mode.name}" <c:if test='${mode.id == mode_id_selected}'>selected</c:if> />
                </c:forEach>
            </select>
            <br />

            <button type="submit">戦績表示</button>
        </form>

        <table>
            <tr>
                <th class="record_user">ユーザー</th>
                <th class="record_game">ゲーム</th>
                <th class="record_mode">モード</th>
                <th class="record_win_or_lose">勝敗</th>
                <th class="record_point">ポイント</th>
                <th class="record_detail">詳細</th>
            </tr>
            <c:forEach var="record" items="${records}">
                <tr>
                    <td class="record_user"><c:out value="${record.user.name}" /></td>
                    <td class="record_game"><c:out value="${record.game.name}" /></td>
                    <td class="record_mode"><c:out value="${record.mode.name}" /></td>
                    <td class="record_win_or_lose"><c:out value="${record.winOrLose}" /></td>
                    <td class="record_point"><c:out value="${record.point}" /></td>
                    <td class="record_detail">詳細</td>
                </tr>
            </c:forEach>
        </table>

        <div class="pagination">
            全 <c:out value="${record_count}" /> 件<br />
            <c:forEach var="i" begin="1" end="${(record_count-1)/maxRow+1}">
                <c:choose>
                    <c:when test="${page == i}">
                        <c:out value="${i}" />
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actTop}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <script>
            //jQuery
            $(function() {
                //セレクトボックスの値が変わったとき
                $("select#game_list").change(
                    function() {
                        $("select#mode_list option").remove();
                        $("select#mode_list").append('<option value=0 label="全て" />');

                        var id = $("select#game_list option:selected").val();
                        if(0 < id) {
                            $.ajax({
                                type: "GET",
                                url: "battle_record_system/?action=Ajax&command=getModeListByGame",
                                data: {game_id: id},
                                success: function(modeJSON) {
                                             var modeList = $.parseJSON(modeJSON);
                                             $.each(modeList, function(key, value) {
                                                 $("select#mode_list").append('<option value="'+value.id+'" label="'+value.name+'" />');
                                             })
                                         }
                            });
                        }
                    }
                );
            });
        </script>
    </c:param>
</c:import>