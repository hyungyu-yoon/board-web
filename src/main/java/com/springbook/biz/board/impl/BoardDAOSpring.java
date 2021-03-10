package com.springbook.biz.board.impl;

import com.springbook.biz.board.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDAOSpring{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final String BOARD_INSERT = "insert into board(seq, title, writer, content) values ((select nvl(max(seq), 0)+1 from board), ?, ?, ?)";
    private final String BOARD_UPDATE = "update board set title=?, contnet=?, where seq=?";
    private final String BOARD_DELETE = "delete from board where seq=?";
    private final String BOARD_GET = "select * from board where seq=?";
    private final String BOARD_LIST = "select * from board order by seq desc";

//    extends JdbcDaoSupport 방식
//    @Autowired
//    public void setSuperDataSource(DataSource dataSource) {
//        super.setDataSource(dataSource);
//    }

    public void insertBoard(BoardVO vo){
        System.out.println("===> JDBC로 insertBoard() 기능 처리");
        jdbcTemplate.update(BOARD_INSERT, vo.getTitle(), vo.getWriter(), vo.getContent());
    }

    public void updateBoard(BoardVO vo){
        System.out.println("===> JDBC로 updateBoard() 기능 처리");
        jdbcTemplate.update(BOARD_UPDATE, vo.getTitle(), vo.getContent(), vo.getSeq());
    }

    public void deleteBoard(BoardVO vo){
        System.out.println("===> JDBC로 deleteBoard() 기능 처리");
        jdbcTemplate.update(BOARD_DELETE, vo.getSeq());
    }

    public BoardVO getBoard(BoardVO vo){
        System.out.println("===> JDBC로 getBoard() 기능 처리");
        Object[] args = {vo.getSeq()};
        return jdbcTemplate.queryForObject(BOARD_GET, args, new BoardRowMapper());
    }

    public List<BoardVO> getBoardList(BoardVO vo){
        System.out.println("===> JDBC로 getBoardList() 기능 처리");
        return jdbcTemplate.query(BOARD_LIST, new BoardRowMapper());
    }
}
