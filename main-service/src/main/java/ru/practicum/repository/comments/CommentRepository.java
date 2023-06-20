package ru.practicum.repository.comments;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.comment.Comment;
import ru.practicum.model.event.Event;
import ru.practicum.model.user.User;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByEvent(Event event, Pageable pageable);

    @Query("select c from Comment as c " +
            "where (:text is null or upper(c.text) like upper(concat('%', :text, '%'))) " +
            "and (:user is null or c.author = :user) " +
            "and (:event is null or c.event = :event) " +
            "order by c.created")
    List<Comment> getCommentsByFilters(@Param("text") String text,
                                       @Param("user") User user,
                                       @Param("event") Event event,
                                       Pageable pageable);
}
