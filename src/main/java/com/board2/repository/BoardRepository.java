package com.board2.repository;

import com.board2.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//JpaRepository를 상속 받고, 상속 받을 때 2개의 인자를<BoardEntity, BoardEntity가 가지고 있는 pk이 타입>
public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
    /*조회수를 증가한다 = (db를 기준으로,mysql기준으로)
     update board_table set board_hits=board_hits+1 where id=? */
    /*BoardEntity자리에 table이 오지마 여기서 entity를 기준으로 하기 때문에 BoardEntity사용
    b= BoardEntity의 약어, boardHits= db의 컬럼명이 아니라 Entity에 정의하 컬럼
    b.id= BoardEntity에 있는 id, :id= @Param("id")
    따라서 BoardEntity에  boardHits값이 접근한다.*/
    @Modifying //update, delect등 쿼리를 실행해야할 때 필수로 사용!
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updatedHits(@Param("id") Long id);
}
