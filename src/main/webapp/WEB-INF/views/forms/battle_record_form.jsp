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

<!-- editの時 -->
<c:if test="${modes == null}">
    <c:set var="modes" value="${record.game.modeList}" />
</c:if>

<table class="form-table">
    <tr class="form-list">
        <th><label for="${AttributeConst.GAME_ID.getValue()}">ゲーム</label></th>
        <td>
            <select id="game_list" name="${AttributeConst.GAME_ID.getValue()}">
                <c:forEach var="game" items="${games}">
                    <c:if test="${game.deleteFlag == AttributeConst.DELETE_FALSE.getIntegerValue()}">
                        <option value="${game.id}" label="${game.name}" <c:if test="${game.id == record.game.id}">selected</c:if> />
                    </c:if>
                </c:forEach>
            </select>
        </td>
    </tr>
    <tr class="form-list">
        <th><label for="${AttributeConst.MODE_ID.getValue()}">モード</label></th>
        <td>
            <select id="mode_list" name="${AttributeConst.MODE_ID.getValue()}">
                <c:forEach var="mode" items="${modes}">
                    <c:if test="${mode.deleteFlag == AttributeConst.DELETE_FALSE.getIntegerValue()}">
                        <option value="${mode.id}" label="${mode.name}" <c:if test="${mode.id == record.mode.id}">selected</c:if> />
                    </c:if>
                </c:forEach>
            </select>
        </td>
    </tr>
    <tr class="form-date">
        <th><label for="${AttributeConst.RECORD_DATETIME.getValue()}">日時</label></th>
        <td><input id="datetime" type="text" name="${AttributeConst.RECORD_DATETIME.getValue()}" value="${datetime}"></td>
    </tr>
    <tr class="form-win-or-lose">
        <th>勝敗</th>
        <td>
            <label for="${AttributeConst.RECORD_WIN.getValue()}">勝利</label>&nbsp;
            <input type="text" size="3" name="${AttributeConst.RECORD_WIN.getValue()}" value="${record.win}"><br />
        </td>
    </tr>
    <tr class="form-win-or-lose">
        <th> </th>
        <td>
            <label for="${AttributeConst.RECORD_LOSE.getValue()}">敗北</label>&nbsp;
            <input type="text" size="3" name="${AttributeConst.RECORD_LOSE.getValue()}" value="${record.lose}"><br />
        </td>
    </tr>
    <tr class="form-win-or-lose">
        <th> </th>
        <td>
            <label for="${AttributeConst.RECORD_DRAW.getValue()}">引分</label>&nbsp;
            <input type="text" size="3" name="${AttributeConst.RECORD_DRAW.getValue()}" value="${record.draw}">
        </td>
    <tr class="form-point">
        <th><label for="${AttributeConst.RECORD_POINT.getValue()}">ポイント</label></th>
        <td><input type="text" size="3" name="${AttributeConst.RECORD_POINT.getValue()}" value="${record.point}">&nbsp;pt</td>
    </tr>
    <tr class="form-memo">
        <th><label for="${AttributeConst.RECORD_MEMO.getValue()}">メモ</label></th>
        <td><textarea name="${AttributeConst.RECORD_MEMO.getValue()}"><c:out value="${record.memo}" /></textarea></td>
    </tr>
</table>

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