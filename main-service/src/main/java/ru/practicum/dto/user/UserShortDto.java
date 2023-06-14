package ru.practicum.dto.user;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserShortDto {
    private Long id;
    private String name;

}
