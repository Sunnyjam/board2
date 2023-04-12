package com.board2.service;

import com.board2.dto.BoardDTO;
import com.board2.entity.BoardEntity;
import com.board2.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//DTO -> Entity로 변환(Entity 클래스에서 작업)  하거나 Entity -> DTO로 변환(DTO 클래스에서 작업) 과정이 생긴다.

/*JPA의 특성(Entity클래스에 담긴 어떤 값들이 db값에 영향을 줄 수 있다)
controller로 호출을 받을 때는 DTO로 넘겨 받고, repository로 넘겨줄 때는 entity로 넘겨준다.
 db의 data를 조회 할 때는 repository로 부터 Entity로 받아 온다. 이것을 controller로 return 줄 때는 DTO로 바꿔서 넘겨준다.*/
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(BoardDTO boardDTO) {
        //BoardEntity.toSaveEntity(boardDTO)를 호출한 결과를 Entity 객체(boardEntity)로 받아 올수 있다.
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
        //DTO객체를 Entity객체로 옮겨 담음.
    }

    public List<BoardDTO> findAll() {
        //Entity로 넘어온 객체(List<BoardEntity>)를 DTO객체(List<BoardDTO>)로 옮겨 담아 controller로 return해줘야함.
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        //return할 객체를 선언
        List<BoardDTO> boardDTOList = new ArrayList<>(); //boardEntityList의 데이터를 boardDTOList에 옮겨 담음.
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
            //for의 Entity객체(BoardEntity)를 DTO(BoardDTO)로 변환하고, 변환된 객체를 boardDTOList에다 받는 작업
        }
        return boardDTOList;
        //for문이 끝나면 boardDTOList로 반환.
    }
}
