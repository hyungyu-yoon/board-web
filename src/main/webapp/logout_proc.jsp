<%--
  Created by IntelliJ IDEA.
  User: yoon
  Date: 2021/03/17
  Time: 12:06 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    session.invalidate();
    response.sendRedirect("login.jsp");
%>