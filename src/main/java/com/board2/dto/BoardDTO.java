package com.board2.dto;

import com.board2.entity.BaseEntity;
import com.board2.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

//Data Transfer Object 데이터 전달 객체
@Getter
@Setter
@ToString //필드값 확인할때
@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    //boardHist=조회수
    private int boardHist;
    private LocalDateTime localCreatedTime;
    private LocalDateTime localupdateTime;

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHist(boardEntity.getBoardHits());
        boardDTO.setLocalCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setLocalupdateTime(boardEntity.getUpdatedTime());
        return boardDTO;
    }

}
