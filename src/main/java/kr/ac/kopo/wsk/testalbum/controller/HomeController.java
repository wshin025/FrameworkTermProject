package kr.ac.kopo.wsk.testalbum.controller;

import kr.ac.kopo.wsk.testalbum.model.Album;
import kr.ac.kopo.wsk.testalbum.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AlbumService service;

    /** 메인 페이지 (앨범 목록) */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("albums", service.findAll());
        return "index";
    }

    /** 상세 페이지 */
    @GetMapping("/album/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Album album = service.findById(id);

        if (album == null) {
            return "redirect:/"; // 없는 앨범 클릭 시 메인으로
        }

        model.addAttribute("album", album);
        return "detail";
    }
}
