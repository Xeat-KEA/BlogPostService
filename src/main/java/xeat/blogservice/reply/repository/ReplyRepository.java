package xeat.blogservice.reply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xeat.blogservice.reply.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
