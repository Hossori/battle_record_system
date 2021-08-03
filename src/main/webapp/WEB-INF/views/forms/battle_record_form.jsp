<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<c:set var="now" value="${LocalDateTime.now().format(DateTimeFormatter.ofPattern('yyyy/MM/dd HH:mm'))}" />

<label for="${AttributeConst.GAME_ID.getValue()}">ゲーム</label>
<select id="game_list">
    <c:forEach var="game" items="${games}">
        <option value="${game.id}" label="${game.name}" <c:if test="${game.id == record.game.id}">selected</c:if> />
    </c:forEach>
</select>
<br />

<label for="${AttributeConst.MODE_ID.getValue()}">モード</label>
<select id="mode_list">
    <c:forEach var="mode" items="${record.game.modeList}">
        <option value="${mode.id}" label="${mode.name}" <c:if test="${mode.id == record.mode.id}">selected</c:if> />
    </c:forEach>
</select>
<br />

<label for="${AttributeConst.RECORD_DATETIME.getValue()}">日時</label>
<input id="datetime" type="text" name="${AttributeConst.RECORD_DATETIME.getValue()}" value="${now}">
<br />

<fieldset name="${AttributeConst.RECORD_WIN_OR_LOSE.getValue()}">
    <legend>勝敗</legend>
    <label for="${AttributeConst.RECORD_WIN.getValue()}">勝利</label>
    <input type="text" size="3" name="${AttributeConst.RECORD_WIN.getValue()}" value="${record.win}">
    <label for="${AttributeConst.RECORD_LOSE.getValue()}">敗北</label>
    <input type="text" size="3" name="${AttributeConst.RECORD_LOSE.getValue()}" value="${record.lose}">
    <label for="${AttributeConst.RECORD_DRAW.getValue()}">引分</label>
    <input type="text" size="3" name="${AttributeConst.RECORD_DRAW.getValue()}" value="${record.draw}">
</fieldset>
<br />

<label for="${AttributeConst.RECORD_POINT.getValue()}">ポイント</label>
<input type="text" size="3" name="${AttributeConst.RECORD_POINT.getValue()}" value="${record.point}">
<br />

<label for="${AttributeConst.RECORD_MEMO.getValue()}">メモ</label>
<textarea name="${AttributeConst.RECORD_MEMO.getValue()}"><c:out value="${record.memo}" /></textarea>

<script>
    $(function() {
        $.datetimepicker.setLocale('ja');
        $('#datetime').datetimepicker({
        });
    });
</script>