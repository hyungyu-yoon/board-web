package com.springbook.view.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @RequestMapping("/logout.do")
    public String handleRequest(HttpSession httpSession) {
        System.out.println("로그아웃 처리");
        httpSession.invalidate();

        return "login.jsp";
    }
}
