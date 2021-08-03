<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actRecord" value="${ForwardConst.ACT_RECORD.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title">戦績登録</c:param>
    <c:param name="content">
        <form method="POST" action="<c:url value='?action=${actRecord}&command=${commCrt}' />">
            <c:import url="/WEB-INF/views/forms/battle_record_form.jsp" />
            <button type="submit">登録</button>
        </form>
    </c:param>
</c:import>