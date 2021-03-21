package com.springbook.view.controller;

import com.springbook.biz.board.BoardVO;
import com.springbook.biz.board.impl.BoardDAO;
import com.springbook.biz.user.UserVO;
import com.springbook.biz.user.impl.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HandlerMapping handlerMapping;
    private ViewResolver viewResolver;

    @Override
    public void init() throws ServletException {
        handlerMapping = new HandlerMapping();
        viewResolver = new ViewResolver();
        viewResolver.setPrefix("./");
        viewResolver.setSuffix(".jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        process(request, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 클라이언트의 요청 path 정보를 추출한다.
        String uri = request.getRequestURI();
        String path = uri.substring(uri.lastIndexOf("/"));

        Controller ctrl = handlerMapping.getController(path);

        String viewName = ctrl.handleRequest(request, response);

        String view = null;
        if(!viewName.contains(".do")) {
            view = viewResolver.getView(viewName);
        } else {
            view = viewName;
        }
        response.sendRedirect(view);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 사용자 입력 정보 추출
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        UserVO vo = new UserVO();
        vo.setId(id);
        vo.setPassword(password);

        UserDAO userDAO = new UserDAO();
        UserVO user = userDAO.getUser(vo);

        if(user != null) {
            response.sendRedirect("getBoardList.do");
        }else {
            response.sendRedirect("login.jsp");
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("login.jsp");
    }

    private void getBoardList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BoardVO vo = new BoardVO();
        BoardDAO dao = new BoardDAO();
        List<BoardVO> boardList = dao.getBoardList(vo);

        HttpSession session = request.getSession();
        session.setAttribute("boardList", boardList);
        response.sendRedirect("getBoardList.jsp");
    }

    private void getBoard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String seq = request.getParameter("seq");
        BoardVO vo = new BoardVO();
        vo.setSeq(Integer.parseInt(seq));
        BoardDAO dao = new BoardDAO();
        BoardVO board = dao.getBoard(vo);

        HttpSession session = request.getSession();
        session.setAttribute("board", board);
        response.sendRedirect("getBoard.jsp");
    }

    private void insertBoard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 사용자 입력 정보 추출
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String writer = request.getParameter("writer");
        String content = request.getParameter("content");

        // 2. DB 연동 처리
        BoardVO vo = new BoardVO();
        vo.setTitle(title);
        vo.setWriter(writer);
        vo.setContent(content);

        BoardDAO boardDAO = new BoardDAO();
        boardDAO.insertBoard(vo);

        response.sendRedirect("getBoardList.do");
    }

    private void updateBoard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 사용자 입력 정보 추출
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String seq = request.getParameter("seq");

        // 2. DB 연동 처리
        BoardVO vo = new BoardVO();
        vo.setTitle(title);
        vo.setContent(content);
        vo.setSeq(Integer.parseInt(seq));

        BoardDAO boardDAO = new BoardDAO();
        boardDAO.updateBoard(vo);

        response.sendRedirect("getBoardList.do");
    }

    private void deleteBoard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 사용자 입력 정보 추출
        request.setCharacterEncoding("UTF-8");
        String seq = request.getParameter("seq");

        // 2. DB 연동 처리
        BoardVO vo = new BoardVO();
        vo.setSeq(Integer.parseInt(seq));

        BoardDAO boardDAO = new BoardDAO();
        boardDAO.deleteBoard(vo);

        response.sendRedirect("getBoardList.do");
    }


}
