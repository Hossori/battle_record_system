<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actUser" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="actRecord" value="${ForwardConst.ACT_RECORD.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDst" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title">戦績編集</c:param>
    <c:param name="content">
        <form method="POST">
            <c:import url="/WEB-INF/views/forms/battle_record_form.jsp" />
        </form>
        <a href="#" onclick="(function(){var cmd='${commUpd}';submit(cmd);})();">登録</a>
        <a href="#" onclick="(function(){var cmd='${commDst}';submit(cmd);})();">削除</a>

        <script>
            function submit(cmd) {
                document.forms[0].action = "<c:url value='?action=${actRecord}&command="+cmd+"&record_id=${record.id}' />";
                document.forms[0].submit();
            }
        </script>
    </c:param>
</c:import>