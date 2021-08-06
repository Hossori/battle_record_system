<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!-- createの際は今の日時をセット -->
<c:choose>
    <c:when test="${record == null}">
        <c:set var="datetime" value="${LocalDateTime.now().format(DateTimeFormatter.ofPattern('yyyy/MM/dd HH:mm'))}" />
    </c:when>
    <c:otherwise>
        <c:set var="datetime" value="${record.datetime.format(DateTimeFormatter.ofPattern('yyyy/MM/dd HH:mm'))}" />
    </c:otherwise>
</c:choose>

<label for="${AttributeConst.GAME_ID.getValue()}">ゲーム</label>
<select id="game_list" name="${AttributeConst.GAME_ID.getValue()}">
    <c:forEach var="game" items="${games}">
        <c:if test="${game.deleteFlag == AttributeConst.DELETE_FALSE.getIntegerValue()}">
            <option value="${game.id}" label="${game.name}" <c:if test="${game.id == record.game.id}">selected</c:if> />
        </c:if>
    </c:forEach>
</select>
<br />

<!-- editの時 -->
<c:if test="${modes == null}">
    <c:set var="modes" value="${record.game.modeList}" />
</c:if>

<label for="${AttributeConst.MODE_ID.getValue()}">モード</label>
<select id="mode_list" name="${AttributeConst.MODE_ID.getValue()}">
    <c:forEach var="mode" items="${modes}">
        <c:if test="${mode.deleteFlag == AttributeConst.DELETE_FALSE.getIntegerValue()}">
            <option value="${mode.id}" label="${mode.name}" <c:if test="${mode.id == record.mode.id}">selected</c:if> />
        </c:if>
    </c:forEach>
</select>
<br />

<label for="${AttributeConst.RECORD_DATETIME.getValue()}">日時</label>
<input id="datetime" type="text" name="${AttributeConst.RECORD_DATETIME.getValue()}" value="${datetime}">
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

<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}">

<script>
    $(function() {
        $.datetimepicker.setLocale('ja');
        $('#datetime').datetimepicker({
        });
    });

    $(function() {
        //セレクトボックスの値が変わったとき
        $("select#game_list").change(
            function() {
                $("select#mode_list option").remove();

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