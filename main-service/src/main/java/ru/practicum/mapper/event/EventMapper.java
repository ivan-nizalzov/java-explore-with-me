package ru.practicum.mapper.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.dto.location.LocationDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.mapper.LocationMapper;
import ru.practicum.mapper.category.CategoryMapper;
import ru.practicum.mapper.user.UserMapper;
import ru.practicum.model.category.Category;
import ru.practicum.model.event.Event;
import ru.practicum.model.location.Location;
import ru.practicum.model.user.User;

import java.time.LocalDateTime;


@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class, LocationMapper.class})
public interface EventMapper {

    default EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(toUserShortDto(event.getInitiator()))
                .location(toLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn((event.getPublishedOn() == null) ? null : event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "annotation", source = "newEventDto.annotation")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "confirmedRequests", constant = "0L")
    @Mapping(target = "description", source = "newEventDto.description")
    @Mapping(target = "createdOn", source = "dateTime")
    @Mapping(target = "eventDate", source = "newEventDto.eventDate")
    @Mapping(target = "initiator", source = "user")
    @Mapping(target = "location", source = "newEventDto.location")
    @Mapping(target = "paid", source = "newEventDto.paid")
    @Mapping(target = "participantLimit", source = "newEventDto.participantLimit")
    @Mapping(target = "requestModeration", source = "newEventDto.requestModeration")
    @Mapping(target = "state", ignore = true) // Will be added by setter
    @Mapping(target = "title", source = "newEventDto.title")
    @Mapping(target = "views", constant = "0L")
    Event toEvent(NewEventDto newEventDto, Category category, User user, LocalDateTime dateTime);

    default EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    default NewCategoryDto toCategoryDto(Category category) {
        return Mappers.getMapper(CategoryMapper.class).toCategoryDto(category);
    }

    default UserShortDto toUserShortDto(User user) {
        return Mappers.getMapper(UserMapper.class).toUserShortDto(user);
    }

    default LocationDto toLocationDto(Location location) {
        return Mappers.getMapper(LocationMapper.class).toLocationDto(location);
    }
}
