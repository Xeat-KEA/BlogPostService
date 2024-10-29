package xeat.blogservice.announce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.announce.entity.Announce;

import java.util.List;

public interface AnnounceRepository extends JpaRepository<Announce, Long> {

    Page<Announce> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
