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


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("albums", service.findAll());
        return "index";
    }


    @GetMapping("/album/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Album album = service.findById(id);

        if (album == null) {
            return "redirect:/";
        }

        model.addAttribute("album", album);
        return "detail";
    }
}
