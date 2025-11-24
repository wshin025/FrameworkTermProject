package kr.ac.kopo.wsk.testalbum.service;

import kr.ac.kopo.wsk.testalbum.model.Album;
import kr.ac.kopo.wsk.testalbum.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository repo;

    public List<Album> findAll() {
        return repo.findAll();
    }

    public Album findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void save(Album album) {
        repo.save(album);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
