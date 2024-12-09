package xeat.blogservice.reply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xeat.blogservice.reply.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT m FROM Reply m WHERE m.parentReplyId IS NULL AND m.article.id = :articleId")
    List<Reply> findParentReplies(@Param("articleId") Long articleId);

    List<Reply> findAllByParentReplyId(Long parentReplyId);

    // parentReplyId가 특정 값인 댓글 개수 조회
    Integer countByParentReplyId(Long parentReplyId);

}