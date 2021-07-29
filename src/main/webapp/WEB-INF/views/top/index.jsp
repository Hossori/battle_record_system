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

        <select id="game">
            <option>1</option>
            <option>2</option>
        </select>

        <script>
        $(function() {
            //セレクトボックスの値が変わったとき
            $("#game").change(
                function() {
                    var data = $.get("battle_record_system/?action=Top&command=i");
                    var option=$("<option>").text(data);
                    $("#game").append(option);
                }
            );
        });
        </script>
    </c:param>
</c:import>