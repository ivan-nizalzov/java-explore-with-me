package ru.practicum.service.request;

import ru.practicum.dto.request.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto createRequestByUser(Long userId, Long requestDto);

    List<ParticipationRequestDto> getRequestsCreatedByUser(Long userId);

    ParticipationRequestDto cancelOwnRequestCreatedByUser(Long userId, Long requestId);
}
