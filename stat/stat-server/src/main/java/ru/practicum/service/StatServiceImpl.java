package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.exception.StartEndDateTimeException;
import ru.practicum.model.EndpointHit;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final EndpointHitMapper endpointHitMapper;

    @Transactional
    public EndpointHitDto addStatistic(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = endpointHitMapper.toEndpointHit(endpointHitDto);
        log.info("Added EndpointHit: {}", endpointHitDto);

        return endpointHitMapper.toEndpointHitDto(statRepository.save(endpointHit));
    }

    public List<ViewStatsDto> getStatistic(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        checkDate(start, end);
        if (uris == null) {
            if (unique) {
                log.info("Received statistics with unique IP");
                return statRepository.getStatisticsWithUniqueIp(start, end).stream()
                        .map(endpointHitMapper::toViewStatsDto)
                        .collect(Collectors.toList());
            } else {
                log.info("Received all statistics");
                return statRepository.getAllStatistics(start, end).stream()
                        .map(endpointHitMapper::toViewStatsDto)
                        .collect(Collectors.toList());
            }
        } else {
            if (unique) {
                log.info("Received statistics with unique IP and URI");
                return statRepository.getStatisticsWithUniqueIpAndUris(start, end, uris).stream()
                        .map(endpointHitMapper::toViewStatsDto)
                        .collect(Collectors.toList());
            } else {
                log.info("Received statistics with URI");
                return statRepository.getAllStatisticsWithUris(start, end, uris).stream()
                        .map(endpointHitMapper::toViewStatsDto)
                        .collect(Collectors.toList());
            }
        }
    }

    private void checkDate(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new StartEndDateTimeException("Error with start time and end time");
        }
    }

}
