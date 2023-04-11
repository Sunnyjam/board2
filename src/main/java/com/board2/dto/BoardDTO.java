package com.board2.dto;

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


}
