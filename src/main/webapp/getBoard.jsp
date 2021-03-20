<%--
  Created by IntelliJ IDEA.
  User: yoon
  Date: 2021/03/20
  Time: 12:20 오전
  To change this template use File | Settings | File Templates.
--%>
<%@page import="com.springbook.biz.board.BoardVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    BoardVO board = (BoardVO) session.getAttribute("board");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>글 상세</title>
</head>
<body>
    <center>
        <h1>글 상세</h1>
        <a href="logout.do">Log-out</a>
        <hr>
        <form action="updateBoard.do" method="post">
            <input name="seq" type="hidden" value="<%=board.getSeq()%>">
            <table border="1" cellspacing="0" cellpadding="0">
                <tr>
                    <td bgcolor="orange" width="70">제목</td>
                    <td align="left"><input name="title" type="text" value="<%=board.getTitle()%>"></td>
                </tr>
                <tr>
                    <td bgcolor="orange">작성자</td>
                    <td align="left"><%=board.getWriter()%></td>
                </tr>
                <tr>
                    <td bgcolor="orange">내용</td>
                    <td align="left">
                        <textarea name="content" cols="40" rows="10"><%= board.getContent()%></textarea>
                    </td>
                </tr>
                <tr>
                    <td bgcolor="orange">등록일</td>
                    <td align="left"><%=board.getRegDate()%></td>
                </tr>
                <tr>
                    <td bgcolor="orange">조회수</td>
                    <td align="left"><%=board.getCnt()%></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="글 수정">
                    </td>
                </tr>
            </table>
        </form>
        <hr>
        <a href="insertBoard.jsp">글등록</a>&nbsp;&nbsp;&nbsp;
        <a href="deleteBoard.do?seq=<%=board.getSeq()%>">글삭제</a>&nbsp;&nbsp;&nbsp;
        <a href="getBoardList.jsp">글목록</a>
    </center>
</body>
</html>
