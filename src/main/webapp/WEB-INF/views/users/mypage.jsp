<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actUser" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="actRecord" value="${ForwardConst.ACT_RECORD.getValue()}" />
<c:set var="commMypage" value="${ForwardConst.CMD_MYPAGE.getValue()}" />
<c:set var="commEntry" value="${ForwardConst.CMD_ENTRY.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title"><c:out value="${user.name}" />の戦績</c:param>
    <c:param name="content">
        <form method="POST" action="<c:url value='?action=${actUser}&command=${commMypage}' />">
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

            <input type="hidden" name="${AttributeConst.USER_ID.getValue()}" value="${user.id}">

            <button type="submit">戦績表示</button>
        </form>

        <c:if test="${user.id == sessionScope.login_user.id}">
            <a href="<c:url value='?action=${actRecord}&command=${commEntry}' />">戦績登録</a>
        </c:if>

        <table>
            <tr>
                <th>勝率</th>
                <td><fmt:formatNumber value="${total.win_rate}" pattern="0.00" />&nbsp;%</td>
            </tr>
            <tr>
                <th>ポイント</th>
                <td><c:out value="${total.point}" />&nbsp;pt</td>
            </tr>
        </table>

        勝敗
        <table>
            <tr>
                <th>対戦数</th>
                <th>勝利</th>
                <th>敗北</th>
                <th>引分</th>
            </tr>
            <tr>
                <td><c:out value="${total.count}" /></td>
                <td><c:out value="${total.win}" /></td>
                <td><c:out value="${total.lose}" /></td>
                <td><c:out value="${total.draw}" /></td>
            </tr>
        </table>

        <table>
            <tr>
                <th class="record_date">日付</th>
                <th class="record_game">ゲーム</th>
                <th class="record_mode">モード</th>
                <th class="record_win_rate">勝率</th>
                <th class="record_point">ポイント</th>
                <th class="record_memo">メモ</th>
            </tr>
            <c:forEach var="record" items="${records}">
                <fmt:parseDate value="${record.datetime}" pattern="yyyy-MM-dd'T'HH:mm" var="recordDate" type="date" />
                <tr>
                    <td class="record_date"><a href="<c:url value='?action=${actRecord}&command=${commShow}&record_id=${record.id}' />">
                        <fmt:formatDate value="${recordDate}" pattern="yyyy/MM/dd" />
                    </a></td>
                    <td class="record_game"><c:out value="${record.game.name}" /></td>
                    <td class="record_mode"><c:out value="${record.mode.name}" /></td>
                    <td class="record_win_rate"><fmt:formatNumber value="${record.winRate}" pattern="0.00" />%</td>
                    <td class="record_point">
                        <c:if test="${record.point != null}"><c:out value="${record.point}" />pt</c:if>
                    </td>
                    <td class="record_memo"><c:if test="${record.memo != null}">あり</c:if></td>
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
                                <a href="<c:url value='?action=${actRecord}&command=${commMypage}&page=${i}' />"><c:out value="${i}" /></a>
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