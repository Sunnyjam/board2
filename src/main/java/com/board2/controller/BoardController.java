package com.board2.controller;

import com.board2.dto.BoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) {
        System.out.println("boardDTO = " + boardDTO);
        return null;
        /*@ModelAttribute에 의해서 BoardDTO클래스 객체를 찾아서,
         save.html의 name의 boardWriter와 BoardDTO클래스의 boardWriter(필드값)가 동일하다면
         spring이 해당하는 필드에 대한 setter를 알아서 호출을 하면서 setter메서드로 각각 담아준다.
         이렇게 DTO객체를 세팅을해준다= 일일이 requestpram으로 받아주지 않아도 하나의 DTO객체로만 받아주면 된다.=입력값을 가져올수있다.*/
    }
}
