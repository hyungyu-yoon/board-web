package com.springbook.view.board;

import com.springbook.biz.board.BoardVO;
import com.springbook.biz.board.impl.BoardDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetBoardListController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("글 목록 검색 처리");

        BoardVO vo = new BoardVO();
        BoardDAO dao = new BoardDAO();
        List<BoardVO> boardList = dao.getBoardList(vo);

//        HttpSession session = request.getSession();
//        session.setAttribute("boardList", boardList);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("boardList", boardList);
        modelAndView.setViewName("getBoardList");
        return modelAndView;
    }
}
