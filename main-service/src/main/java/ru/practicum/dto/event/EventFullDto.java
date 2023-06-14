package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.location.LocationDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.model.event.State;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.util.Util.YYYY_MM_DD_HH_MM_SS;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class EventFullDto {
    private Long id;
    private String annotation;
    private NewCategoryDto category;
    private String description;
    private Long confirmedRequests;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime createdOn;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private LocationDto location;
    private Boolean paid;
    private Long participantLimit;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    @NotNull
    @Length(min = 3, max = 120)
    private String title;
    private Long views;
}
