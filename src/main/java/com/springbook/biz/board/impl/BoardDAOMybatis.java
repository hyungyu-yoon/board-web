package com.springbook.biz.board.impl;

import com.springbook.biz.board.BoardVO;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDAOMybatis extends SqlSessionDaoSupport {
    @Autowired
    public void setSqlsessionFactory(SqlSessionFactory sqlsessionFactory) {
        super.setSqlSessionFactory(sqlsessionFactory);
    }

    public void insertBoard(BoardVO vo){
        getSqlSession().insert("BoardDAOMybatis.insertBoard",vo);
    }

    public void updateBoard(BoardVO vo){
        getSqlSession().update("BoardDAOMybatis.updateBoard",vo);
    }

    public void deleteBoard(BoardVO vo){
        getSqlSession().delete("BoardDAOMybatis.deleteBoard",vo);
    }

    public BoardVO getBoard(BoardVO vo){
        return (BoardVO) getSqlSession().selectOne("BoardDAOMybatis.getBoard",vo);
    }

    public List<BoardVO> getBoardList(BoardVO vo){
        return getSqlSession().selectList("BoardDAOMybatis.getBoardList", vo);
    }
}
