<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actUser" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="actRecord" value="${ForwardConst.ACT_RECORD.getValue()}" />
<c:set var="commMypage" value="${ForwardConst.CMD_MYPAGE.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title">戦績詳細</c:param>
    <c:param name="content">
        <table>
            <tr>
                <th class="record_user">ユーザー</th>
                <c:choose>
                    <c:when test="${record.user.deleteFlag == AttributeConst.DELETE_FALSE.getIntegerValue()}">
                        <td class="record_user">
                            <a href="<c:url value='?action=${actUser}&command=${commShow}&user_id=${record.user.id}' />">
                                <c:out value="${record.user.name}" />
                            </a>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td class="record_user"><c:out value="${record.user.name}" /></td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <fmt:parseDate value="${record.datetime}" pattern="yyyy-MM-dd'T'HH:mm" var="recordDatetime" type="both" />
                <th class="record_datetime">日時</th>
                <td class="record_datetime"><fmt:formatDate value="${recordDatetime}" pattern="yyyy/MM/dd HH:mm" /></td>
            </tr>
            <tr>
                <th class="record_game">ゲーム</th>
                <td class="record_game"><c:out value="${record.game.name}" /></td>
            </tr>
            <tr>
                <th class="record_mode">モード</th>
                <td class="record_mode"><c:out value="${record.mode.name}" /></td>
            </tr>
            <tr>
                <th class="record_win_rate">勝率</th>
                <td class="record_win_rate"><fmt:formatNumber value="${record.winRate}" pattern="0.00" />%</td>
            </tr>
            <tr>
                <th class="record_win_or_lose">勝敗</th>
                <td class="record_win_or_lose">
                    <c:out value="${record.win}" />勝&nbsp;
                    <c:out value="${record.lose}" />敗&nbsp;
                    <c:out value="${record.draw}" />分
                </td>
            </tr>
            <tr>
                <th class="record_point">ポイント</th>
                <td class="record_point">
                    <c:if test="${record.point != null}"><c:out value="${record.point}" />pt</c:if>
                </td>
            </tr>
            <tr>
                <th class="record_memo">メモ</th>
                <td class="record_memo"><pre><c:out value="${record.memo}" /></pre></td>
            </tr>
        </table>

        <c:if test="${sessionScope.login_user.id == record.user.id}">
            <a href="<c:url value='?action=${actRecord}&command=${commEdit}&record_id=${record.id}' />">編集</a>
        </c:if>
    </c:param>
</c:import>