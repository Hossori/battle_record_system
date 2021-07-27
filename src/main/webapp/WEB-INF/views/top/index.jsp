<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="title">みんなの戦績</c:param>
    <c:param name="content">
        <div>id : <c:out value="${user.id}" /></div>
        <div>email : <c:out value="${user.email}" /></div>
        <div>name : <c:out value="${user.name}" /></div>
        <div>password : <c:out value="${user.password}" /></div>
        <div>adminFlag : <c:out value="${user.adminFlag}" /></div>
        <div>deleteFlag : <c:out value="${user.deleteFlag}" /></div>
    </c:param>
</c:import>