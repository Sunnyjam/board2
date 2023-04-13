 # 개발환경
1. IDE:Intellij IDEACommunity
2. Spring Boot
3. mySQL
4. Spring Data JPA
5. Thtmeleaf

# 게시판 주요기능
1. 글쓰기(/board/save)
2. 글목록(/board/)
3. 글조회(/board/{id})
4. 글수정(/board/update/{id})
   - 상세화면에서 수정버튼 클릭
   - 서버에서 해당 게시글의 정보를 가지고 수정 화며 출력
   - 제목과 내용을 수정 입력 받아서 서버로 요청
   - 수정 처리
5. 글삭제(/board/delete/{id})
6. 페이징처리(/board/paging)
   - /board/paging?page=2
   - 게시글14개, 한페이지에5개= 총 3페이지 필요