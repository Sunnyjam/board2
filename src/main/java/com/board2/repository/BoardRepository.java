package com.board2.repository;

import com.board2.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
//JpaRepository를 상속 받고, 상속 받을 때 2개의 인자를<BoardEntity, BoardEntity가 가지고 있는 pk이 타입>
public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
}
