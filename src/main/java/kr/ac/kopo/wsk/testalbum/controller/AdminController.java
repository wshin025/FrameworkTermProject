package kr.ac.kopo.wsk.testalbum.controller;

import jakarta.servlet.http.HttpSession;
import kr.ac.kopo.wsk.testalbum.model.Album;
import kr.ac.kopo.wsk.testalbum.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AlbumService service;
    private final String uploadDir = System.getProperty("user.home") + "/album_images";


    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("admin") != null) {
            return "redirect:/admin";
        }
        return "admin_login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        if (username.equals("admin") && password.equals("1234")) {
            session.setAttribute("admin", true);
            return "redirect:/admin";
        }

        model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "admin_login";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }


    @GetMapping
    public String adminHome(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("albums", service.findAll());
        return "admin";
    }


    @GetMapping("/add")
    public String addPage(HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        return "admin_add";
    }


    @PostMapping("/add")
    public String addAlbum(@RequestParam String title,
                           @RequestParam String description,
                           @RequestParam MultipartFile file,
                           HttpSession session) throws Exception {

        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        file.transferTo(new File(uploadDir, fileName));

        Album album = new Album(title, "/images/" + fileName, description);
        service.save(album);

        return "redirect:/admin";
    }


    @GetMapping("/edit/{id}")
    public String editAlbum(@PathVariable Long id,
                            HttpSession session,
                            Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        Album album = service.findById(id);
        if (album == null) {
            return "redirect:/admin";
        }

        model.addAttribute("album", album);
        return "admin_edit";
    }


    @PostMapping("/update")
    public String updateAlbum(@RequestParam Long id,
                              @RequestParam String title,
                              @RequestParam String description,
                              @RequestParam(required = false) MultipartFile file,
                              HttpSession session) throws Exception {

        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        Album album = service.findById(id);
        if (album == null) {
            return "redirect:/admin";
        }

        album.setTitle(title);
        album.setDescription(description);

        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            file.transferTo(new File(uploadDir, fileName));

            album.setImageUrl("/images/" + fileName);
        }

        service.save(album);
        return "redirect:/admin";
    }


    @GetMapping("/delete/{id}")
    public String deleteAlbum(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        service.delete(id);
        return "redirect:/admin";
    }
}
