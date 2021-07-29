<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js" ></script>
    </head>
    <body>
        <h2 id="title"><c:out value="${param.title}" /></h2>

        <c:if test="${flush != null}">
            <div id="flush"><c:out value='${flush}' /></div>
        </c:if>
        <c:if test="${errors != null}">
            <div id="errors">
                <c:forEach var="error" items="${errors}">
                   <c:out value="${error}" /><br/>
                </c:forEach>
            </div>
        </c:if>

        <div id="content">${param.content}</div>
    </body>
</html>