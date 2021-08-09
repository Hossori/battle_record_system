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
            <input type="hidden" name="${AttributeConst.RECORD_ID.getValue()}" value="${record.id}">
            <button type="button" onclick="update();">登録</button>
            <button type="button" onclick="destroy();">削除</button>
        </form>

        <script>
            function destroy() {
                if(confirm("削除してよろしいですか？")) {
                    document.forms[0].action = "<c:url value='?action=${actRecord}&command=destroy&record_id=${record.id}' />";
                    document.forms[0].submit();
                }
            }
            function update() {
                document.forms[0].action = "<c:url value='?action=${actRecord}&command=update&record_id=${record.id}' />";
                document.forms[0].submit();
            }
        </script>
    </c:param>
</c:import>