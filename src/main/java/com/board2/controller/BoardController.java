package com.board2.controller;

import com.board2.dto.BoardDTO;
import com.board2.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    //생성자 주입 방식으로 의존성을 주입 받는다.
    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) {
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "index";
        /*@ModelAttribute에 의해서 BoardDTO클래스 객체를 찾아서,
         save.html의 name의 boardWriter와 BoardDTO클래스의 boardWriter(필드값)가 동일하다면
         spring이 해당하는 필드에 대한 setter를 알아서 호출을 하면서 setter메서드로 각각 담아준다.
         이렇게 DTO객체를 세팅을해준다= 일일이 requestpram으로 받아주지 않아도 하나의 DTO객체로만 받아주면 된다.=입력값을 가져올수있다.*/
    }

    @GetMapping("/")
    public String findAll(Model model) {
        //DB에서 전체 게시글 데이터를 가져와 list.html에 보여준다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
        //Service의 return(boardDTOList)을 받아와서 model(model.addAttribute("boardList")에 담아와서 return "list"의 html에 넘어가게 된다.
    }

    @GetMapping("/{id}") //게시글 상세 조회
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        /* 해당 게시글의 조회수를 하나 올리고, 게시글 데이터를 가져와서 detail.html에 출력*/
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "detail";
    }
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
    return "updat";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "detail";
//        return "redirect:/board/" + boardDTO.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/";
    }

    // /board/paging?page=1,@PageableDefault(page = 1)는 /board/paging?page=1의page=1가 없더라도 page=1를 준다는 의미
    @GetMapping("/paging") //Pageable import시 잘 선택하기,
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
//        pageable.getPageNumber();
        //Page import해주기
        Page<BoardDTO> boardList = boardService.paging(pageable);
        //페이지 갯수가 총 20개, 현재 사용자가 3페이지를 보고 있다면(123중에서 3페이지를 다르게 표현),현재페이지7(789)
        //총 페이지가 8이면 89마 보여줘야함, 9는 보여지면 안됨.=endPage로 표현
        int blockLimit = 3; //아래 보여지는 페이지 번호 갯수
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit -1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 :  boardList.getTotalPages();
        //startPage=현재 사용자가 사용중인 페이를 blockLimit로 나눠서 소숫점을 올리는 처리를 해줌=1,4,7,10,...
        //endPage= 123,456,789 ...값을 표현해줌, 사망연산자

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";
    }
}
