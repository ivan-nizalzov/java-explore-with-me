package ru.practicum.mapper.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.model.comment.Comment;
import ru.practicum.model.event.Event;
import ru.practicum.model.user.User;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "authorName", source = "eventComment.author.name")
    CommentDto toCommentDto(Comment eventComment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", source = "event")
    @Mapping(target = "author", source = "user")
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "created", source = "dateTime")
    Comment toComment(NewCommentDto commentDto, Event event, User user, LocalDateTime dateTime);
}
