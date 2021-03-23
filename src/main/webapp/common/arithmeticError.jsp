<%--
  Created by IntelliJ IDEA.
  User: yoon
  Date: 2020/12/06
  Time: 11:23 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  language="java" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>기본 에러 화면</title>
</head>
<body bgcolor="#fffff" text="#000000">
  <table width="100%" border="1" cellpadding="0" cellspacing="0">
    <tr>
      <td align="center" bgcolor="orange"><b>기본 에러 화면입니다.</b></td>
    </tr>
  </table>


  <table width="100%" border="1" cellpadding="0" cellspacing="0">
    <tr>
      <td align="center">
        <br><br><br><br><br>
        Message: ${exception.message}
        <br><br><br><br><br>
      </td>
    </tr>
  </table>
</body>
</html>
