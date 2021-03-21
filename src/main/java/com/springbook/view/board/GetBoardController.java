package com.springbook.view.board;

import com.springbook.biz.board.BoardVO;
import com.springbook.biz.board.impl.BoardDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetBoardController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("글 상세 조회 처리");

        String seq = request.getParameter("seq");
        BoardVO vo = new BoardVO();
        vo.setSeq(Integer.parseInt(seq));
        BoardDAO dao = new BoardDAO();
        BoardVO board = dao.getBoard(vo);

//        HttpSession session = request.getSession();
//        session.setAttribute("board", board);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("board", board);
        modelAndView.setViewName("getBoard");
        return modelAndView;
    }
}
