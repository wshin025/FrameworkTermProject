package kr.ac.kopo.wsk.testalbum.repository;

import kr.ac.kopo.wsk.testalbum.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
