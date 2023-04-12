package com.board2.controller;

import com.board2.dto.BoardDTO;
import com.board2.service.BoardService;
import lombok.RequiredArgsConstructor;
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
}
