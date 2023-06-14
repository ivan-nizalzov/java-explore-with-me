package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.dto.location.LocationDto;
import ru.practicum.model.event.StateAction;

import java.time.LocalDateTime;

import static ru.practicum.util.Util.YYYY_MM_DD_HH_MM_SS;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventDto {
    @Length(min = 20, max = 2000)
    private String annotation;
    private Long category;
    @Length(min = 20, max = 7000)
    private String description;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime eventDate;
    private LocationDto location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    @Length(min = 3, max = 120)
    private String title;

}
