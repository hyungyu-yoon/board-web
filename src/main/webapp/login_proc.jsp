<%--
  Created by IntelliJ IDEA.
  User: yoon
  Date: 2021/03/17
  Time: 12:06 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.springbook.biz.user.impl.UserDAO" %>
<%@ page import="com.springbook.biz.user.UserVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // 1. 사용자 입력 정보 추출
    String id = request.getParameter("id");
    String password = request.getParameter("password");

    UserVO vo = new UserVO();
    vo.setId(id);
    vo.setPassword(password);

    UserDAO userDAO = new UserDAO();
    UserVO user = userDAO.getUser(vo);

    if(user != null) {
        response.sendRedirect("getBoardList.jsp");
    }else {
        response.sendRedirect("login.jsp");
    }
%>