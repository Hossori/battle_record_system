<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actUser" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="actRecord" value="${ForwardConst.ACT_RECORD.getValue()}" />
<c:set var="commMypage" value="${ForwardConst.CMD_MYPAGE.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title">みんなの戦績</c:param>
    <c:param name="content">
        <form method="POST" action="<c:url value='?action=${actTop}&command=${commIdx}' />">
            <label for="${AttributeConst.GAME_ID.getValue()}">ゲーム</label><br />
            <select id="game_list" name="${AttributeConst.GAME_ID.getValue()}">
                <option value=0 label="全て" />
                <c:forEach var="game" items="${games}">
                    <option value="${game.id}" label="${game.name}" <c:if test='${game.id == game_id_selected}'>selected</c:if> />
                </c:forEach>
            </select>
            <br />

            <label for="${AttributeConst.MODE_ID.getValue()}">モード</label><br />
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
                <th class="record_date">日付</th>
                <th class="record_user">ユーザー</th>
                <th class="record_game">ゲーム</th>
                <th class="record_mode">モード</th>
                <th class="record_win_rate">勝率</th>
                <th class="record_point">ポイント</th>
            </tr>
            <c:forEach var="record" items="${records}">
                <fmt:parseDate value="${record.datetime}" pattern="yyyy-MM-dd'T'HH:mm" var="recordDate" type="date" />
                <tr>
                    <td class="record_date"><a href="<c:url value='?action=${actRecord}&command=${commShow}&record_id=${record.id}' />">
                        <fmt:formatDate value="${recordDate}" pattern="yyyy/MM/dd" />
                    </a></td>
                    <td class="record_user">
                        <c:choose>
                            <c:when test="${record.user.deleteFlag == AttributeConst.DELETE_FALSE.getIntegerValue()}">
                                <a href="<c:url value='?action=${actUser}&command=${commShow}&user_id=${record.user.id}' />">
                                   <c:out value="${record.user.name}" />
                                </a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${record.user.name}" />
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="record_game"><c:out value="${record.game.name}" /></td>
                    <td class="record_mode"><c:out value="${record.mode.name}" /></td>
                    <td class="record_win_rate"><fmt:formatNumber value="${record.winRate}" pattern="0.00" />%</td>
                    <td class="record_point">
                        <c:if test="${record.point != null}"><c:out value="${record.point}" />pt</c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <div class="pagination">
            全 <c:out value="${record_count}" /> 件<br />
            <c:choose>
                <c:when test="${record_count == 0}">
                    1
                </c:when>
                <c:otherwise>
                    <c:forEach var="i" begin="1" end="${(record_count-1)/maxRow+1}" step="1">
                        <c:choose>
                            <c:when test="${page == i}">
                                <c:out value="${i}" />
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='?action=${actTop}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <script type="text/javascript">
            //select#game_listの値が変わったとき、select#mode_list内のoptionタグを更新する
            $(function() {
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