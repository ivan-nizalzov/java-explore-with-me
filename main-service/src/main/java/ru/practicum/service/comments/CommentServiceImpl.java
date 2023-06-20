package ru.practicum.service.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.comment.CommentMapper;
import ru.practicum.model.comment.Comment;
import ru.practicum.model.event.Event;
import ru.practicum.model.event.State;
import ru.practicum.model.user.User;
import ru.practicum.repository.comments.CommentRepository;
import ru.practicum.repository.event.EventRepository;
import ru.practicum.repository.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentDto createCommentForEvent(Long userId, Long eventId, NewCommentDto commentDto) {
        User user = checkUserInDb(userId);
        Event event = findEventById(eventId);

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Cannot add comment: the event with id=" + eventId +
                    " is not published yet");
        }

        Comment eventComment = commentMapper.toComment(commentDto, event, user, LocalDateTime.now());
        Comment newEventComment = commentRepository.save(eventComment);

        return commentMapper.toCommentDto(newEventComment);
    }

    public CommentDto findCommentById(Long commentId) {
        Comment eventComment = getCommentById(commentId);

        return commentMapper.toCommentDto(eventComment);
    }

    @Transactional
    public void deleteCommentByUser(Long userId, Long eventId, Long commentId) {
        User user = checkUserAndEvent(userId, eventId);
        Comment eventComment = getCommentById(commentId);

        if (!eventComment.getAuthor().getId().equals(user.getId())) {
            throw new ConflictException("Only the author may delete comment with id=" + commentId +
                    ". User with id=" + userId + " is not the author");
        }

        commentRepository.deleteById(commentId);
    }

    @Transactional
    public CommentDto updateCommentByUser(Long userId, Long eventId, Long commentId, NewCommentDto commentDto) {
        User user = checkUserAndEvent(userId, eventId);
        Comment eventComment = getCommentById(commentId);

        if (!eventComment.getAuthor().getId().equals(user.getId())) {
            throw new ConflictException("Only the author may update comment with id=" + commentId +
                    ". User with id=" + userId + " is not the author");
        }

        eventComment.setText(commentDto.getText());
        Comment updatedEventComment = commentRepository.save(eventComment);

        return commentMapper.toCommentDto(updatedEventComment);
    }

    public List<CommentDto> getCommentForEventById(Long eventId, Integer from, Integer size) {
        Event event = findEventById(eventId);
        PageRequest page = PageRequest.of(from, size);
        List<Comment> commentList = commentRepository.findByEvent(event, page);

        return commentList.stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByFilters(String text, Long eventId, Long userId, Integer from, Integer size) {
        User user = null;
        Event event = null;
        PageRequest page = PageRequest.of(from, size);

        if (userId != null) {
            user = checkUserInDb(userId);
        }
        if (eventId != null) {
            event = findEventById(eventId);
        }

        List<Comment> eventComments = commentRepository.getCommentsByFilters(text, user, event, page);

        return eventComments.stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCommentByAdmin(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public CommentDto updateCommentByAdmin(Long commentId, NewCommentDto commentDto) {
        Comment eventComment = getCommentById(commentId);

        eventComment.setText(commentDto.getText());
        Comment updatedEventComment = commentRepository.save(eventComment);

        return commentMapper.toCommentDto(updatedEventComment);
    }

    private User checkUserInDb(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
    }

    private Event findEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));
    }

    private User checkUserAndEvent(Long userId, Long eventId) {
        findEventById(eventId);
        return checkUserInDb(userId);
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id=" + commentId + " not found"));
    }
}
