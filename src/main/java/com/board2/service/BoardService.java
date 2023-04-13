package com.board2.service;

import com.board2.dto.BoardDTO;
import com.board2.entity.BoardEntity;
import com.board2.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updatedHits(id);
    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        }else {
            return null;
        }

    }

    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page= pageable.getPageNumber() - 1;//page 위치에 있는 값은 0부터 시작하기 때문에 -1을 해야 요청한 값과 일치함.
        int pageLimit = 3;
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        /*findAll에서 전달되는 값= page(몇 페이지를 보고 싶은지),pageLimit(한페이지에 보여줄 글 갯수)
          Sort.by= 전체를 Sort.Direction.DESC, "id"기준으로 정렬을 해서 해당 페이지 값을 가져온다,
          id = 기준컬럼, db 컬럼기준이 아니라 entityd에 있는 기준
          따라서 한페이지당 3개씩 글을 보여주고 정렬 기준을 id기준으로 내림차순 정렬을 의미함.*/
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent());// 요처 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements());//전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber());//db로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPage() = " + boardEntities.getTotalPages());//전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize());//한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious());//이젠 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst());//첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast());//마지막 페이지 여부
        //목록: id,writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS = boardEntities.map(board-> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        // board객체를 꺼내서 BoardDTO객체로 옮겨 담는과정
        return boardDTOS;
    }
}
