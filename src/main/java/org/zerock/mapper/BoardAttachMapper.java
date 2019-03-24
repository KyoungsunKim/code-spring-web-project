package org.zerock.mapper;

import org.zerock.domain.BoardAttachVO;

import java.util.List;

public interface BoardAttachMapper {

    int insert(BoardAttachVO vo);

    int delete(String uuid);

    List<BoardAttachVO> findByBno(Long bno);
}
