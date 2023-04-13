package com.board2.entity;

//DB의 테이블 역할을 하는 클래스, db와 연관, service와 repository에서 사용(com.board2에서)
//Service 클래스 까지만 오도록 코드 짜기

import com.board2.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_table") //BoardEntity가 BaseEntity를 상속받게 사용
public class BoardEntity extends BaseEntity {
    @Id //pk column 지정, 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increament
    private Long id;

    @Column(length = 20, nullable = false)//컬럼크기, 크기는 20, not null
    private String boardWriter;

    @Column // @Column만 있으며 크기는 255, not null 가느
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    //BoardService에서 Entity클래스 객체를 넘겨주기 위하 작업
    //=DTO에 담긴 값들을 Entity 객체로 옮겨 담는 작업
    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHist());
        return boardEntity;
    }
    /*id가 있으며 update쿼리, toSaveEntity와 toUpdateEntity의 차이점= save(update,insert기능을 함)
    id가 없으면 save쿼리 */
}
