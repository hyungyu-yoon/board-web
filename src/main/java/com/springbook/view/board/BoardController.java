package com.springbook.view.board;

import com.springbook.biz.board.BoardService;
import com.springbook.biz.board.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @RequestMapping("/insertBoard.do")
    public String insertBoard(BoardVO vo) throws IOException {
        System.out.println("글 등록 처리");

        MultipartFile uploadFile = vo.getUploadFile();
        if(!uploadFile.isEmpty()) {
            String fileName = uploadFile.getOriginalFilename();
            uploadFile.transferTo(new File("/Users/yoon/file/" + fileName));
        }

        boardService.insertBoard(vo);
        return "getBoardList.do";
    }

    @RequestMapping("/updateBoard.do")
    public String updateBoard(@ModelAttribute("board") BoardVO boardVo) {
        System.out.println("글 수정 처리");

        System.out.println(boardVo.getSeq());
        System.out.println(boardVo.getTitle());
        System.out.println(boardVo.getWriter());
        System.out.println(boardVo.getContent());
        System.out.println(boardVo.getRegDate());
        System.out.println(boardVo.getCnt());

        boardService.updateBoard(boardVo);
        return "getBoardList.do";
    }

    @RequestMapping("/deleteBoard.do")
    public String deleteBoard(BoardVO boardVO) {
        System.out.println("글 삭제 처리");


        boardService.deleteBoard(boardVO);

        return "getBoardList.do";
    }

    @ModelAttribute("conditionMap")
    public Map<String, String> searchConditionMap() {
        Map<String, String> conditionMap = new HashMap<>();
        conditionMap.put("제목", "TITLE");
        conditionMap.put("내용", "CONTENT");
        return conditionMap;
    }

    @RequestMapping("/getBoard.do")
    public String getBoard(BoardVO boardVO, Model model) {
        System.out.println("글 상세 조회 처리");

        model.addAttribute("board", boardService.getBoard(boardVO));
        return "getBoard.jsp";
    }

    @RequestMapping("/getBoardList.do")
    public String getBoardList(BoardVO boardVO, Model model) {

        if (boardVO.getSearchCondition() == null) {
            boardVO.setSearchCondition("TITLE");
        }
        if (boardVO.getSearchKeyword() == null) {
            boardVO.setSearchKeyword("");
        }

        model.addAttribute("boardList", boardService.getBoardList(boardVO));
        return "getBoardList.jsp";
    }

    @RequestMapping("/dataTransform.do")
    @ResponseBody
    public List<BoardVO> dataTransform(BoardVO vo) {
        vo.setSearchCondition("TITLE");
        vo.setSearchKeyword("");
        List<BoardVO> boardList = boardService.getBoardList(vo);
        return boardList;
    }
}
