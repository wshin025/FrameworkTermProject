package kr.ac.kopo.wsk.testalbum.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.MalformedURLException;

@Controller
public class ImageController {

    private final String uploadDir = System.getProperty("user.home") + "/album_images";

    @GetMapping("/images/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> showImage(@PathVariable String fileName) throws MalformedURLException {
        File file = new File(uploadDir, fileName);

        if (!file.exists()) {
            return ResponseEntity.notFound().build(); // 파일 없을 때 404 (서버 에러 안 터짐)
        }

        Resource resource = new UrlResource(file.toURI());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG) // 필요하면 파일 확장자 보고 바꾸기
                .body(resource);
    }
}
